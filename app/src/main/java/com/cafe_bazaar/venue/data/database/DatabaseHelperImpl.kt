package com.cafe_bazaar.venue.data.database

import android.util.Log
import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.cafe_bazaar.venue.utils.JsonUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DatabaseHelperImpl @Inject constructor(
    private val database: VenueDatabase,
    private val jsonUtils: JsonUtils
): DatabaseHelper {

    override suspend fun getVenueByOffset(offset: Int): Flow<GetVenueListRes?> = flow {
        val venuesPerPage = database.getVenuesPerPageDao().getVenuesListByOffset(offset)
        // try to map string json to array of Venues
        val result: GetVenueListRes? = jsonUtils.toObject(venuesPerPage.jsonString)
        Log.i("===>>>", "getVenueByOffset")
        emit(result)
    }

    override suspend fun insertVenue(offset: Int, venues: GetVenueListRes): Flow<Boolean> = flow {
        database.getVenuesPerPageDao().insert(VenuesPerPage(offset, venues.toString()))
        Log.i("===>>>", "insertVenue")
        emit(true)
    }

    override suspend fun clearVenueTable(): Flow<Boolean> = flow {
        database.getVenuesPerPageDao().clearAllData()
        Log.i("===>>>", "insertVenue")
        emit(true)
    }
}