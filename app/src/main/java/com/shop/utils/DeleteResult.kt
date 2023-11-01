package com.shop.utils

sealed class DeleteResult<T>(val data: T? = null) {
    class Success<T>(data: T? = null) : DeleteResult<T>(data)
    class Failure<T> : DeleteResult<T>()
    class Error<T> : DeleteResult<T>()
}