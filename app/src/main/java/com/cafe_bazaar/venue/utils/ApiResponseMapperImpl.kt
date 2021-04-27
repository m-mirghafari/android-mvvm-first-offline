package com.cafe_bazaar.venue.utils

import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.BaseResponse
import com.cafe_bazaar.venue.data.models.DataState
import javax.inject.Inject

class ApiResponseMapperImpl @Inject constructor(): ApiResponseMapper {

    override fun <T> map(apiRes: BaseResponse<T>): ApiResponse<T> {
        try {
            return if (apiRes.isSuccess()) {
                ApiResponse<T>().apply {
                    this.success = true
                    this.data = apiRes.response
                    this.message = "success :D"
                }

            } else { ApiResponse<T>().apply {
                    this.success = false
                    this.data = apiRes.response
                    this.message = "failed :P"
                }
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return ApiResponse<T>().apply {
            this.success = false
            this.data = null
            this.message = "some error happened!"
        }
    }

    override fun <T> getConnectionErrorMapResponse(e: Exception): ApiResponse<T> {
        return ApiResponse<T>().apply {
            this.success = false
            this.data = null
            this.message = "connection error!"
        }
    }
}