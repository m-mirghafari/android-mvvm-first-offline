package com.cafe_bazaar.venue.data.network

import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueDetailsRes
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    suspend fun getVenues(offset: Int, latitudeAndLongitude: String): Flow<DataState<ApiResponse<GetVenueListRes>>>
    suspend fun getVenueDetails(venueId: String): Flow<DataState<ApiResponse<GetVenueDetailsRes>>>
}