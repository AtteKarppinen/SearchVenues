package com.example.searchvenues.model

import com.example.searchvenues.interfaces.Contract
import com.example.searchvenues.interfaces.Permissions
import com.example.searchvenues.model.entities.Venue
import com.example.searchvenues.model.template.Result
import com.example.searchvenues.model.usecase.GetLatLongDataUseCase
import com.example.searchvenues.model.usecase.GetLocationPermissionStateUseCase
import com.example.searchvenues.model.usecase.GetVenuesUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Main interactor implementing [Contract.Interactor]. Exists only to simplify MainPresenter
 * (Other option would be to call all use cases from the presenter directly)
 */
class MainInteractor(
    private val getVenuesUseCase: GetVenuesUseCase,
    private val getLocationPermissionStateUseCase: GetLocationPermissionStateUseCase,
    private val getLatLongDataUseCase: GetLatLongDataUseCase
) : Contract.Interactor {

    companion object {
        // In case of an error, use Helsinki lat/long data
        const val HELSINKI_LL_BACKUP = "60.2,25"
    }

    override suspend fun getVenues(query: String?): Result<List<Venue>?> {
        var latLong = ""
        val job = GlobalScope.launch {
            latLong = when (val result = updateLatLongData()) {
                is Result.Success -> result.data
                is Result.Error -> HELSINKI_LL_BACKUP
            }
        }
        // Wait for job coroutine before executing getVenuesUseCase
        job.join()
        return getVenuesUseCase(listOf(latLong, query))
    }


    override suspend fun getLocationPermissionState(): Result<Permissions.PermissionState> {
        return getLocationPermissionStateUseCase(Unit)
    }

    override suspend fun updateLatLongData(): Result<String> {
        return getLatLongDataUseCase(Unit)
    }
}