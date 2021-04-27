package com.cafe_bazaar.venue.utils

import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import javax.inject.Inject


class JsonUtilsImpl @Inject constructor(private var gson: Gson): JsonUtils {

    override fun toString(data: Any) : String {
        return gson.toJson(data)
    }

    override fun toGetVenueListRes(jsonString: String): GetVenueListRes? {
        return try {
            val typeToken = object : TypeToken<GetVenueListRes>() {}.type
            val result: GetVenueListRes? = gson.fromJson(jsonString, typeToken)
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}