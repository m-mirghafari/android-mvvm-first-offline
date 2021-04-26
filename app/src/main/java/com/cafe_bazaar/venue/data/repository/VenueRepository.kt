package com.cafe_bazaar.venue.data.repository

import android.location.Location
import com.cafe_bazaar.venue.app.Constants
import com.cafe_bazaar.venue.data.database.DatabaseHelper
import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueDetailsRes
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.cafe_bazaar.venue.data.network.ApiHelper
import com.cafe_bazaar.venue.data.sp.SharedPreferenceHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class VenueRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper,
    private val sp: SharedPreferenceHelper
) {

    suspend fun getPlaces(offset: Int, location: Location): Flow<DataState<ApiResponse<GetVenueListRes>>> = flow {
        val lastLocation = Location("")
        lastLocation.latitude = sp.getDouble(Constants.PREF_KEY_LAST_LOCATION_LATITUDE)
        lastLocation.longitude = sp.getDouble(Constants.PREF_KEY_LAST_LOCATION_LONGITUDE)

        var result: GetVenueListRes? = null
        var getDataFromApi = false

        if (location.distanceTo(lastLocation) > 100) {
            apiHelper.getVenues(offset = offset, latitudeAndLongitude = "${location.latitude},${location.longitude}").onEach {
                when (it) {
                    is DataState.Result<ApiResponse<GetVenueListRes>> -> {
                        if (it.result.success) {
                            result = it.result.data
                            getDataFromApi = true
                        }
                    }

                    DataState.Loading -> {
                        emit(DataState.Loading)
                    }
                }
            }
        } else {
            databaseHelper.getVenueByOffset(offset).onEach {
                if (it != null) {
                    result = it
                    getDataFromApi = false
                }
            }
        }

        if (result == null) {
            apiHelper.getVenues(offset = offset, latitudeAndLongitude = "${location.latitude},${location.longitude}").onEach {
                when (it) {
                    is DataState.Result<ApiResponse<GetVenueListRes>> -> {
                        if (it.result.success) {
                            result = it.result.data
                            getDataFromApi = true
                        }
                    }

                    DataState.Loading -> {
                        emit(DataState.Loading)
                    }
                }
            }
        }

        if (offset == 0) databaseHelper.clearVenueTable()
        if (getDataFromApi && result != null) databaseHelper.insertVenue(offset = offset, venues = result!!)





        emit(DataState.Result(ApiResponse<GetVenueListRes>().apply {
            this.success = result != null
            this.data = result
            this.message = if (this.success) "success :D" else "failed :P"
        }))
    }

    suspend fun getVenueDetails(venueId: String): Flow<DataState<ApiResponse<GetVenueDetailsRes>>> =
        apiHelper.getVenueDetails(venueId = venueId)
}
