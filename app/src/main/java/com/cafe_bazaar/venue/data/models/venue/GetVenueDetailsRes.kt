package com.cafe_bazaar.venue.data.models.venue

import com.google.gson.annotations.SerializedName

data class GetVenueDetailsRes (
    @SerializedName("venue") val venue : Venue
)