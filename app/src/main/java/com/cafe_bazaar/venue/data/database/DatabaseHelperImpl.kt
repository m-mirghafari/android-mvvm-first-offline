package com.cafe_bazaar.venue.data.database

import android.util.Log
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.cafe_bazaar.venue.utils.JsonUtils
import java.lang.Exception
import javax.inject.Inject


class DatabaseHelperImpl @Inject constructor(
    private val database: VenueDatabase,
    private val jsonUtils: JsonUtils
): DatabaseHelper {

    override suspend fun getVenueByOffset(offset: Int): GetVenueListRes? {
        return try {
            val jsonString: String = database.getVenuesPerPageDao().getVenuesListByOffset(offset)?.jsonString ?: ""
            var result: GetVenueListRes? = null
            if (jsonString.isNotEmpty()) result = jsonUtils.toObject<GetVenueListRes>(jsonString)
            Log.i("===>>>", "getVenueByOffset")
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun insertVenue(offset: Int, venues: GetVenueListRes): Boolean {
        database.getVenuesPerPageDao().insert(VenuesPerPage(offset, jsonUtils.toString(venues)))
        Log.i("===>>>", "insertVenue")
        return true
    }

    override suspend fun clearVenueTable(): Boolean {
        database.getVenuesPerPageDao().clearAllData()
        Log.i("===>>>", "insertVenue")
        return true
    }
}