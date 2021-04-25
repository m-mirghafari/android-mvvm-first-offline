package com.cafe_bazaar.venue.data.network

import com.cafe_bazaar.venue.app.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(value = "venues/explore?")
    suspend fun getVenues(
        @Query("client_id") clientId: String = Constants.CLIENT_ID,
        @Query("client_secret") clientSecret: String = Constants.CLIENT_SECRET,
        @Query("v") v: String = Constants.API_V_PARAM,
        @Query("limit") limit: Int = Constants.VENUES_LIST_LIMIT,
        @Query("offset") offset: Int = 1,
        @Query("ll") latitudeAndLongitude: String
    ): Any


    @GET(value = "venues/{venueId}?")
    suspend fun getVenueDetails(
        @Path("venueId") venueId: String,
        @Query("client_id") clientId: String = Constants.CLIENT_ID,
        @Query("client_secret") clientSecret: String = Constants.CLIENT_SECRET,
        @Query("v") v: String = Constants.API_V_PARAM
    ): Any
}