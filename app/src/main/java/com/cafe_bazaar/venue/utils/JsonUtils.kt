package com.cafe_bazaar.venue.utils


interface JsonUtils {

    fun toString(data: Any) : String
    fun <T>toObject(jsonString: String): T
}