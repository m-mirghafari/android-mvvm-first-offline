package com.cafe_bazaar.venue.data.models.venue

import com.google.gson.annotations.SerializedName


data class Categories (
    @SerializedName("id") val id : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("pluralName") val pluralName : String?,
    @SerializedName("shortName") val shortName : String?,
    @SerializedName("primary") val primary : Boolean
)