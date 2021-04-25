package com.cafe_bazaar.venue.utils

import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.BaseResponse
import com.cafe_bazaar.venue.data.models.DataState
import javax.inject.Inject

class ApiResponseMapperImpl @Inject constructor(): ApiResponseMapper {

    override fun <T> map(apiRes: BaseResponse<T>): DataState<ApiResponse<T>> {
        try {
            return if (apiRes.isSuccess()) {
                DataState.Result(ApiResponse<T>().apply {
                    this.success = true
                    this.data = apiRes.response
                    this.message = "success :D"
                })

            } else {
                DataState.Result(ApiResponse<T>().apply {
                    this.success = false
                    this.data = apiRes.response
                    this.message = "failed :P"
                })
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return DataState.Result(ApiResponse<T>().apply {
            this.success = false
            this.data = null
            this.message = "some error happened!"
        })
    }

    override fun <T> getConnectionErrorMapResponse(e: Exception): DataState<ApiResponse<T>> {
        return DataState.Result(ApiResponse<T>().apply {
            this.success = false
            this.data = null
            this.message = "connection error!"
        })
    }
}