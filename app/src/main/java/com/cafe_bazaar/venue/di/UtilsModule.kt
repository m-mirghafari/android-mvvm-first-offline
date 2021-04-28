package com.cafe_bazaar.venue.di

import android.content.Context
import androidx.room.Room
import com.cafe_bazaar.venue.app.Constants
import com.cafe_bazaar.venue.data.database.DatabaseHelper
import com.cafe_bazaar.venue.data.database.DatabaseHelperImpl
import com.cafe_bazaar.venue.data.database.VenueDatabase
import com.cafe_bazaar.venue.data.sp.SharedPreferenceHelper
import com.cafe_bazaar.venue.data.sp.SharedPreferenceHelperImpl
import com.cafe_bazaar.venue.utils.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module

@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideResponseMapper(apiResponseMapper: ApiResponseMapperImpl): ApiResponseMapper = apiResponseMapper

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VenueDatabase =
        Room.databaseBuilder(context, VenueDatabase::class.java, Constants.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDatabaseHelper(databaseHelper: DatabaseHelperImpl): DatabaseHelper = databaseHelper

    @Provides
    @Singleton
    fun provideSharedPref(sharedPreferenceHelper: SharedPreferenceHelperImpl): SharedPreferenceHelper = sharedPreferenceHelper

    @Provides
    @Singleton
    fun provideJsonUtils(jsonUtils: UtilsImpl): Utils = jsonUtils

    @Provides
    @Singleton
    fun provideLocationUtils(locationUtils: LocationUtilsImpl): LocationUtils = locationUtils
}