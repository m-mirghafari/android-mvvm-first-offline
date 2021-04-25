package com.cafe_bazaar.venue.data.database

import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {
    suspend fun getVenueByOffset(offset: Int): Flow<DataState<ApiResponse<GetVenueListRes>>>
    suspend fun saveVenue(offset: Int, venues: GetVenueListRes): Flow<Boolean>
    suspend fun clearVenueTable(): Flow<Boolean>
}