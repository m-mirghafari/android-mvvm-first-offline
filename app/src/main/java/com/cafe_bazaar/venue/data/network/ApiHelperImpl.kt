package com.cafe_bazaar.venue.data.network

import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueDetailsRes
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.cafe_bazaar.venue.utils.ApiResponseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiResponseMapper: ApiResponseMapper
) : ApiHelper {

    override suspend fun getVenues(offset: Int, latitudeAndLongitude: String): ApiResponse<GetVenueListRes> =
        try {
            apiResponseMapper.map(
                apiService.getVenues(
                    offset = offset,
                    latitudeAndLongitude = latitudeAndLongitude
                )
            )
        } catch (e: Exception) {
            apiResponseMapper.getConnectionErrorMapResponse<GetVenueListRes>(e)
        }


    override suspend fun getVenueDetails(venueId: String): ApiResponse<GetVenueDetailsRes> =
        try {
            apiResponseMapper.map(apiService.getVenueDetails(venueId))
        } catch (e: Exception) {
            apiResponseMapper.getConnectionErrorMapResponse<GetVenueDetailsRes>(e)
        }

}
