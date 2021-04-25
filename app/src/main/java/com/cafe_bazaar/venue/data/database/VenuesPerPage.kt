package com.cafe_bazaar.venue.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class VenuesPerPage(
    @PrimaryKey
    var offset: Int = 0,
    var jsonString: String = ""
)