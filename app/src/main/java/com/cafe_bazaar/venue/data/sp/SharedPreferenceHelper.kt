package com.cafe_bazaar.venue.data.sp


interface SharedPreferenceHelper {
    fun setBoolean(key: String, value: Boolean)
    fun setString(key: String, value: String)
    fun setInt(key: String, value: Int)
    fun getBoolean(key: String): Boolean
    fun getString(key: String): String
    fun getInt(key: String): Int
    fun delete(key: String)
}
