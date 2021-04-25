package com.cafe_bazaar.venue.utils

import android.app.Activity
import android.location.Location
import android.location.LocationManager


interface LocationUtils {

    fun getCurrentLocation(
        activity: Activity,
        listener: LocationUtilsListener,
        provider: String = LocationManager.NETWORK_PROVIDER,
        minTimeInMilliSecond: Long = 10000,
        minDistanceInMiter: Float = 100f,
        getLocationUpdates: Boolean = false)

    fun isGpsEnables(activity: Activity): Boolean

    fun requestToTurnOnGps(activity: Activity, requestCode: Int = 10321)
    fun stopUpdateLocation()
}


interface LocationUtilsListener {
    fun onLocationChanged(location: Location)
    fun onProviderDisabled(provider: String)
}