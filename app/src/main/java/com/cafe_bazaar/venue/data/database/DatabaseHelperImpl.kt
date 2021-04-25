package com.cafe_bazaar.venue.data.database

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

    override suspend fun getVenueByOffset(offset: Int): Flow<DataState<ApiResponse<GetVenueListRes>>> {
        val venuesPerPage = database.getVenuesPerPageDao().getVenuesListByOffset(offset)
        // try to map string json to array of Venues
        val result: GetVenueListRes? = jsonUtils.toObject(venuesPerPage.jsonString)
        return flow {
            DataState.Result(ApiResponse<GetVenueListRes>().apply {
                this.success = true
                this.data = result
                this.message = "success :D"
            })
        }
    }

    override suspend fun saveVenue(offset: Int, venues: GetVenueListRes): Flow<Boolean> = database.getVenuesPerPageDao().insert(
        VenuesPerPage(offset, venues.toString())
    )

    override suspend fun clearVenueTable(): Flow<Boolean> = database.getVenuesPerPageDao().clearAllData()
}