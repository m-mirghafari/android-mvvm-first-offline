package com.cafe_bazaar.venue.utils

import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.BaseResponse
import com.cafe_bazaar.venue.data.models.DataState

interface ApiResponseMapper {
    fun <T> map(apiRes: BaseResponse<T>): DataState<ApiResponse<T>>
    fun <T> getConnectionErrorMapResponse(e: Exception): DataState<ApiResponse<T>>
}