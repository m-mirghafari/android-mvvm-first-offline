package com.cafe_bazaar.venue.data.models


sealed class DataState<out R> {
    data class Result<out T>(val result: T) : DataState<T>()
    object Loading : DataState<Nothing>()
}