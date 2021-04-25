package com.cafe_bazaar.venue.data.sp

import android.content.Context
import com.cafe_bazaar.venue.app.Constants


class SharedPreferenceHelperImpl(
    mContext: Context,
    prefFileName: String = Constants.SP_FILE_NAME
) : SharedPreferenceHelper {

    private var mSharesPreferences =
        mContext.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun setBoolean(key: String, value: Boolean) {
        mSharesPreferences.edit().putBoolean(key, value).apply()
    }

    override fun setString(key: String, value: String) {
        mSharesPreferences.edit().putString(key, value).apply()
    }

    override fun setInt(key: String, value: Int) {
        mSharesPreferences.edit().putInt(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean =
        mSharesPreferences.getBoolean(key, false)

    override fun getString(key: String): String =
        mSharesPreferences.getString(key, "") ?: ""

    override fun getInt(key: String): Int =
        mSharesPreferences.getInt(key, 0)

    override fun delete(key: String) =
        mSharesPreferences.edit().remove(key).apply()

    companion object {
        const val PREF_KEY_LAST_LOCATION_LATITUDE = "PREF_KEY_LAST_LOCATION_LATITUDE"
        const val PREF_KEY_LAST_LOCATION_LONGITUDE = "PREF_KEY_LAST_LOCATION_LONGITUDE"
    }
}