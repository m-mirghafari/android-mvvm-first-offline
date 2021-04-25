package com.cafe_bazaar.venue.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class JsonUtilsImpl @Inject constructor(private var gson: Gson): JsonUtils {

    override fun toString(data: Any) : String = gson.toJson(data)

    override fun <T>toObject(jsonString: String): T {
        val typeToken = object : TypeToken<T>() {}.type
        return Gson().fromJson(jsonString, typeToken)
    }
}