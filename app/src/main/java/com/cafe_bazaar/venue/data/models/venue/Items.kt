package com.cafe_bazaar.venue.data.models.venue

import com.google.gson.annotations.SerializedName

data class Items (
//    @SerializedName("reasons") val reasons : String,
    @SerializedName("venue") val venue : Venue,
    @SerializedName("referralId") val referralId : String
)
