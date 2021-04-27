package com.cafe_bazaar.venue.data.repository

import android.location.Location
import android.util.Log
import com.cafe_bazaar.venue.app.Constants
import com.cafe_bazaar.venue.data.database.DatabaseHelper
import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueDetailsRes
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.cafe_bazaar.venue.data.network.ApiHelper
import com.cafe_bazaar.venue.data.sp.SharedPreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class VenueRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper,
    private val sp: SharedPreferenceHelper
) {

    suspend fun getPlaces(
        offset: Int,
        location: Location
    ): Flow<DataState<ApiResponse<GetVenueListRes>>> = flow {
        emit(DataState.Loading)

        try {
            val lastLocation = Location("")
            lastLocation.latitude = sp.getDouble(Constants.PREF_KEY_LAST_LOCATION_LATITUDE)
            lastLocation.longitude = sp.getDouble(Constants.PREF_KEY_LAST_LOCATION_LONGITUDE)

            var result: GetVenueListRes?
            var getDataFromApi: Boolean = false


            if (location.distanceTo(lastLocation) > 100) {
                result = apiHelper.getVenues(
                    offset = offset,
                    latitudeAndLongitude = "${location.latitude},${location.longitude}"
                ).data
                getDataFromApi = true

            } else {
                result = databaseHelper.getVenueByOffset(offset)
                if (result != null) getDataFromApi = false
            }

            if (result == null) {
                result = apiHelper.getVenues(
                    offset = offset,
                    latitudeAndLongitude = "${location.latitude},${location.longitude}"
                ).data
                getDataFromApi = true
            }

            if (offset == 0 && getDataFromApi) databaseHelper.clearVenueTable()
            if (getDataFromApi && result != null) {
                databaseHelper.insertVenue(
                    offset = offset,
                    venues = result
                )

                sp.setDouble(Constants.PREF_KEY_LAST_LOCATION_LATITUDE, location.latitude)
                sp.setDouble(Constants.PREF_KEY_LAST_LOCATION_LONGITUDE, location.longitude)
            }


            emit(DataState.Result(ApiResponse<GetVenueListRes>().apply {
                this.success = result != null
                this.data = result
                this.message = if (this.success) "success :D" else "failed :P"
            }))

        } catch (e: Exception) {
            e.printStackTrace()

            emit(DataState.Result(ApiResponse<GetVenueListRes>().apply {
                this.success = false
                this.data = null
                this.message = "failed :P"
            }))
        }
    }

    suspend fun getVenueDetails(venueId: String): Flow<DataState<ApiResponse<GetVenueDetailsRes>>> = flow {
        emit(DataState.Loading)
        emit(DataState.Result(apiHelper.getVenueDetails(venueId = venueId)))
    }
}
