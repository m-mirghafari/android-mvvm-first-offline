package com.cafe_bazaar.venue.data.models.venue

import com.google.gson.annotations.SerializedName


data class GetVenueListRes (
    @SerializedName("suggestedRadius") val suggestedRadius : Int,
    @SerializedName("headerLocation") val headerLocation : String,
    @SerializedName("headerFullLocation") val headerFullLocation : String,
    @SerializedName("headerLocationGranularity") val headerLocationGranularity : String,
    @SerializedName("totalResults") val totalResults : Int,
//    @SerializedName("suggestedBounds") val suggestedBounds : String,
    @SerializedName("groups") val groups : ArrayList<Groups>
)