package com.cafe_bazaar.venue.utils

import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes


interface Utils {

    fun toString(data: Any) : String
    fun toGetVenueListRes(jsonString: String): GetVenueListRes?
    fun getPastDaysFromToday(date: Long): Int
}