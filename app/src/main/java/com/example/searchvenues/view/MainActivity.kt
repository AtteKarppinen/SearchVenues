package com.example.searchvenues.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.*
import com.example.searchvenues.R
import com.example.searchvenues.interfaces.Contract
import com.example.searchvenues.model.entities.Venue
import com.example.searchvenues.view.list.VenueResultsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Implements [Contract.View]
 */
class MainActivity : AppCompatActivity(), Contract.View {

    private val mainPresenter: Contract.Presenter by inject { parametersOf(this) }

    private val locationPermission: String = Manifest.permission.ACCESS_FINE_LOCATION

    private lateinit var request: ActivityResultLauncher<Intent>
    private lateinit var snackBarAction: () -> Unit
    private lateinit var venueResultsAdapter: VenueResultsAdapter

    companion object {
        private const val LOG_TAG = "MainActivity"

        private const val LOCATION_PERMISSION_REQUEST_CODE = 125
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter.attachView(this)

        venueResultsAdapter = VenueResultsAdapter()
        venue_list_view.apply {
            adapter = venueResultsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, OrientationHelper.VERTICAL))
        }

        search_bar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(searchWord: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainPresenter.onSearchChange(searchWord.toString())
            }
        })

        // Required for launching app settings later
         request = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            mainPresenter.checkOnLaunch()
        }

        snackBarAction = { requestLocationPermission() }
        mainPresenter.checkOnLaunch()
    }

    override fun showProgress() {
        Log.d(LOG_TAG, "showProgress")
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Log.d(LOG_TAG, "hideProgress")
        progress_bar.visibility = View.GONE
    }

    override fun loadListView(venues: List<Venue>?) {
        Log.d(LOG_TAG, "Loading new venues")
        venueResultsAdapter.submitList(venues)
    }

    override fun emptySearchResults() {
        loadListView(null)
    }

    override fun lockUI() {
        search_bar.inputType = InputType.TYPE_NULL
    }

    override fun unlockUI() {
        search_bar.inputType = InputType.TYPE_CLASS_TEXT
    }

    override fun requestLocationPermission() {
        requestPermissions(arrayOf(locationPermission), LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun displayPermissionRationale() {
        Log.d(LOG_TAG, "displayPermissionRationale")
        Snackbar.make(
            root_layout,
            getString(R.string.location_permission_rationale),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.snackbar_action) { snackBarAction.invoke() }
            .show()
    }

    private fun openApplicationSettings() {
        Log.d(LOG_TAG, "openApplicationSettings")
        // Check permission on callback
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", applicationContext.packageName, null)
        request.launch(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.any { it == PackageManager.PERMISSION_DENIED }) {
                // !shouldShowRequestPermissionRationale means user tapped "Do not ask again"
                snackBarAction =
                    if (!shouldShowRequestPermissionRationale(locationPermission)) {
                        { openApplicationSettings() }
                    } else {
                        { requestLocationPermission() }
                    }

                displayPermissionRationale()
            } else {
                // Permission granted
                unlockUI()
                mainPresenter.updateLastKnownLocation()
            }
        }
    }
}