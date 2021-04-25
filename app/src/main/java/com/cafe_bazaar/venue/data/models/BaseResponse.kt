package com.cafe_bazaar.venue.data.models

import com.google.gson.annotations.SerializedName


data class BaseResponse<T> (
    @SerializedName("meta") val meta : Meta,
    @SerializedName("response") val response : T
) {
    fun isSuccess(): Boolean {
        return meta.code in 200..299
    }

}