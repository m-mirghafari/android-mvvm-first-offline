package com.cafe_bazaar.venue.data.models

data class ApiResponse<T>(var success: Boolean = false,
                          var data: T? = null,
                          var message: String = "")
