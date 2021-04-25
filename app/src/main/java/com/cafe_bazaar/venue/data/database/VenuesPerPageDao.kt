package com.cafe_bazaar.venue.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface VenuesPerPageDao {

    @Query("SELECT * FROM VenuesPerPage WHERE `offset` = :offset")
    suspend fun getVenuesListByOffset(offset: Int): VenuesPerPage

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: VenuesPerPage)

    @Query("DELETE FROM VenuesPerPage")
    suspend fun clearAllData()
}