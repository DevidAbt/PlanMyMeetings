package com.example.planmymeetings.screens.add_appointment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.planmymeetings.R
import com.example.planmymeetings.databinding.ActivityAddAppointmentBinding
import com.example.planmymeetings.utils.NotificationUtil


class AddAppointmentActivity : AppCompatActivity() {
    private val LOG_TAG = AddAppointmentActivity::class.java.name

    private lateinit var binding: ActivityAddAppointmentBinding
    private lateinit var viewModel: AddAppointmentViewModel

    private lateinit var notificationUtil: NotificationUtil

    private lateinit var locationManager: LocationManager
    private var accessLocationRequestCode = 15

    private var activityFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment)

        viewModel = ViewModelProvider(this).get(AddAppointmentViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.eventFinishActivity.observe(this, {
            if (it) {
                activityFinished = true
                finish()
            }
        })

        notificationUtil = NotificationUtil(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        getLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!activityFinished) {
            notificationUtil.send("Add appointment")
        }
    }

    override fun onResume() {
        super.onResume()
        notificationUtil.cancel()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                accessLocationRequestCode
            )
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0L,
            0f,
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    Log.d(LOG_TAG, "${location.longitude}, ${location.latitude}")
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }

                override fun onProviderEnabled(provider: String) {
                    super.onProviderEnabled(provider)
                    Log.d(LOG_TAG, "provider enabled")
                }

                override fun onProviderDisabled(provider: String) {
                    Log.d(LOG_TAG, "provider disabled")
                }

            }
        )

        val lastKnownLocation =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lastKnownLocation != null) {
            Log.d(
                LOG_TAG,
                "last known location: ${lastKnownLocation.longitude}, ${lastKnownLocation.latitude}"
            )
            viewModel.relatedPlace.value =
                "${lastKnownLocation.longitude}, ${lastKnownLocation.latitude}"

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == accessLocationRequestCode) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLocation()
                PackageManager.PERMISSION_DENIED -> return
            }
        }
    }
}