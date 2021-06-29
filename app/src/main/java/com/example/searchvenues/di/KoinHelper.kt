package com.example.searchvenues.di

import com.example.searchvenues.BuildConfig
import com.example.searchvenues.interfaces.Contract
import com.example.searchvenues.interfaces.Location
import com.example.searchvenues.interfaces.Permissions
import com.example.searchvenues.interfaces.REST
import com.example.searchvenues.model.*
import com.example.searchvenues.model.usecase.GetLatLongDataUseCase
import com.example.searchvenues.model.usecase.GetLocationPermissionStateUseCase
import com.example.searchvenues.model.usecase.GetVenuesUseCase
import com.example.searchvenues.presenter.MainActivityPresenter
import org.koin.core.module.Module
import org.koin.dsl.module

private val modelModules = listOf(
    module {
        factory<Contract.Interactor> {
            MainInteractor(
                getVenuesUseCase = get(),
                getLocationPermissionStateUseCase = get(),
                getLatLongDataUseCase = get()
            )
        }

        factory<Permissions> {
            PermissionsImplementation(
                context = get()
            )
        }

        factory<Location> {
            LocationImplementation(
                context = get()
            )
        }

        factory<REST> {
            RestImplementation()
        }

        factory {
            GetVenuesUseCase(
                rest = get(),
                apiAccessPoint = BuildConfig.API_ACCESS_POINT,
                apiVersioningDate = BuildConfig.API_VERSIONING_DATE
            )
        }

        factory {
            GetLocationPermissionStateUseCase(
                permissions = get()
            )
        }

        factory {
            GetLatLongDataUseCase(
                location = get()
            )
        }
    }
)

private val presenterModules = listOf(
    module {
        factory<Contract.Presenter> {
            MainActivityPresenter(
                mainInteractor = get()
            )
        }
    }
)

val koinModules: List<Module> = modelModules + presenterModules