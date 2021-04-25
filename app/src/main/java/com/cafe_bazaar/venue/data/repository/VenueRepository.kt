package com.cafe_bazaar.venue.data.repository

import com.cafe_bazaar.venue.data.database.DatabaseHelper
import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueDetailsRes
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.cafe_bazaar.venue.data.network.ApiHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VenueRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) {

    suspend fun getPlaces(offset: Int, latitude: Double, longitude: Double): Flow<DataState<ApiResponse<GetVenueListRes>>> =
        apiHelper.getVenues(offset = offset, latitudeAndLongitude = "$latitude,$longitude")

    suspend fun getVenueDetails(venueId: String): Flow<DataState<ApiResponse<GetVenueDetailsRes>>> =
        apiHelper.getVenueDetails(venueId = venueId)
}
