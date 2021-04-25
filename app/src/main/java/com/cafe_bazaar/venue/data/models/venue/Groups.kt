package com.cafe_bazaar.venue.data.models.venue

import com.google.gson.annotations.SerializedName


data class Groups (
    @SerializedName("type") val type : String,
    @SerializedName("name") val name : String,
    @SerializedName("items") val items : ArrayList<Items>
)