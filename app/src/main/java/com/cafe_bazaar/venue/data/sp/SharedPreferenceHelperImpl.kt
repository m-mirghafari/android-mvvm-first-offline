package com.cafe_bazaar.venue.data.sp

import android.content.Context
import com.cafe_bazaar.venue.app.Constants
import java.lang.Exception
import javax.inject.Inject


class SharedPreferenceHelperImpl @Inject constructor(context: Context) : SharedPreferenceHelper {

    private var sharesPreferences =
        context.getSharedPreferences(Constants.SP_FILE_NAME, Context.MODE_PRIVATE)

    override fun setBoolean(key: String, value: Boolean) {
        sharesPreferences.edit().putBoolean(key, value).apply()
    }

    override fun setString(key: String, value: String) {
        sharesPreferences.edit().putString(key, value).apply()
    }

    override fun setInt(key: String, value: Int) {
        sharesPreferences.edit().putInt(key, value).apply()
    }

    override fun setDouble(key: String, value: Double) {
        sharesPreferences.edit().putString(key, value.toString()).apply()
    }

    override fun getBoolean(key: String): Boolean =
        sharesPreferences.getBoolean(key, false)

    override fun getString(key: String): String =
        sharesPreferences.getString(key, "") ?: ""

    override fun getInt(key: String): Int =
        sharesPreferences.getInt(key, 0)

    override fun getDouble(key: String): Double = try {
        (sharesPreferences.getString(key, "0") ?: "0").toDouble()
    } catch (e: Exception) {
        0.0
    }

    override fun delete(key: String) =
        sharesPreferences.edit().remove(key).apply()
}