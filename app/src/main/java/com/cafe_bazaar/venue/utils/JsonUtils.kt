package com.cafe_bazaar.venue.utils

import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes


interface JsonUtils {

    fun toString(data: Any) : String
    fun toGetVenueListRes(jsonString: String): GetVenueListRes?
}