package com.cafe_bazaar.venue.data.models.venue

import com.google.gson.annotations.SerializedName

data class Venue (
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("location") val location : Location,
    @SerializedName("categories") val categories : ArrayList<Categories> = arrayListOf(),
)