package com.cafe_bazaar.venue.data.models

import com.google.gson.annotations.SerializedName


data class Meta (
    @SerializedName("code") val code : Int,
    @SerializedName("requestId") val requestId : String
)
