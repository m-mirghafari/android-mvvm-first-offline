package com.cafe_bazaar.venue.di

import com.cafe_bazaar.venue.data.database.DatabaseHelper
import com.cafe_bazaar.venue.data.database.DatabaseHelperImpl
import com.cafe_bazaar.venue.data.sp.SharedPreferenceHelper
import com.cafe_bazaar.venue.data.sp.SharedPreferenceHelperImpl
import com.cafe_bazaar.venue.utils.ApiResponseMapper
import com.cafe_bazaar.venue.utils.ApiResponseMapperImpl
import com.cafe_bazaar.venue.utils.LocationUtils
import com.cafe_bazaar.venue.utils.LocationUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun provideResponseMapper(apiResponseMapper: ApiResponseMapperImpl): ApiResponseMapper = apiResponseMapper

    @Provides
    @Singleton
    fun provideDatabaseHelper(databaseHelper: DatabaseHelperImpl): DatabaseHelper = databaseHelper

    @Provides
    @Singleton
    fun provideLocationUtils(locationUtils: LocationUtilsImpl): LocationUtils = locationUtils

    @Provides
    @Singleton
    fun provideSharedPref(sharedPreferenceHelper: SharedPreferenceHelperImpl): SharedPreferenceHelper = sharedPreferenceHelper

}