package com.cafe_bazaar.venue.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.StringReader
import javax.inject.Inject


class JsonUtilsImpl @Inject constructor(private var gson: Gson): JsonUtils {

    override fun toString(data: Any) : String {
        val result = gson.toJson(data)
        Log.e("===>>>>", result)
        return result
    }

    override fun <T>toObject(jsonString: String): T {
        Log.e("===>>>>", jsonString)
        val typeToken = object : TypeToken<T>() {}.type

        val gson = Gson()
        val reader = JsonReader(StringReader(jsonString))
        reader.isLenient = true
        return gson.fromJson(reader, typeToken)
    }
}