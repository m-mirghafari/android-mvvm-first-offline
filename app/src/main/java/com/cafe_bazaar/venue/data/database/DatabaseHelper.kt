package com.cafe_bazaar.venue.data.database

import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes

interface DatabaseHelper {

    suspend fun getVenueByOffset(offset: Int): GetVenueListRes?
    suspend fun insertVenue(offset: Int, venues: GetVenueListRes): Boolean
    suspend fun clearVenueTable(): Boolean
}