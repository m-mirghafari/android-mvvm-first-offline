package com.cafe_bazaar.venue.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [VenuesPerPage::class], version = 1)
abstract class VenueDatabase: RoomDatabase() {

    abstract fun getVenuesPerPageDao(): VenuesPerPageDao

}