package com.cafe_bazaar.venue.data.models.venue

import com.google.gson.annotations.SerializedName
import java.util.ArrayList


data class Location (
    @SerializedName("address") val address : String,
    @SerializedName("crossStreet") val crossStreet : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("distance") val distance : Int,
    @SerializedName("postalCode") val postalCode : Int,
    @SerializedName("cc") val cc : String,
    @SerializedName("city") val city : String,
    @SerializedName("country") val country : String,
    @SerializedName("formattedAddress") val formattedAddress : ArrayList<String>
)