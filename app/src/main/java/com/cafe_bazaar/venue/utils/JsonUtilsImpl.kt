package com.cafe_bazaar.venue.utils

import android.util.Log
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.StringReader
import java.lang.Exception
import javax.inject.Inject


class JsonUtilsImpl @Inject constructor(private var gson: Gson): JsonUtils {

    override fun toString(data: Any) : String {
        val result = gson.toJson(data)
        Log.e("===>>>>", result)
        return result
    }

    override fun toGetVenueListRes(jsonString: String): GetVenueListRes? {
        try {
            Log.e("===>>>>", jsonString)
            val typeToken = object : TypeToken<GetVenueListRes>() {}.type
//        val gson = Gson()
//        val reader = JsonReader(StringReader(jsonString))
//        reader.isLenient = true
            return gson.fromJson(jsonString, typeToken)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("===>>>>", "Failed to toGetVenueListRes")
            return null
        }
    }
}