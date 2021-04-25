package com.cafe_bazaar.venue.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import javax.inject.Inject

class LocationUtilsImpl  @Inject constructor() : LocationUtils {

    private var locationManager: LocationManager? =  null
    private var locationListener: LocationListener? = null

    override fun getCurrentLocation(
        activity: Activity,
        listener: LocationUtilsListener,
        provider: String,
        minTimeInMilliSecond: Long,
        minDistanceInMiter: Float,
        getLocationUpdates: Boolean
    ) {
        try {
            stopUpdateLocation()

            locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    listener.onLocationChanged(location)
                    if (!getLocationUpdates && locationListener != null)
                        stopUpdateLocation()

                }

                override fun onProviderDisabled(provider: String) {
                    listener.onProviderDisabled(provider)
                }
            }


            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) return

            locationManager?.requestLocationUpdates(
                provider,
                minTimeInMilliSecond,
                minDistanceInMiter,
                locationListener!!
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun isGpsEnables(activity: Activity): Boolean {
        return try {
            val locationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun requestToTurnOnGps(activity: Activity, requestCode: Int) {
        val googleApiClient =
            GoogleApiClient.Builder(activity).addApi(LocationServices.API).build()
        googleApiClient.connect()

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 30 * 1000.toLong()
        locationRequest.fastestInterval = 5 * 1000.toLong()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            .setResultCallback { result ->
                val status: Status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> { }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                        try {
                            status.startResolutionForResult(activity, requestCode)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
    }

    override fun stopUpdateLocation() {
        locationManager?.removeUpdates(locationListener!!)
    }

}