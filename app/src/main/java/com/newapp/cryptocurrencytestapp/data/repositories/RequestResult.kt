package com.newapp.cryptocurrencytestapp.data.repositories

import com.newapp.cryptocurrencytestapp.data.ErrorType

sealed class RequestResult<out T> {
    data class Success<out T>(val value: T) : RequestResult<T>()
    data class Error(val code: Int = 0, val error: Throwable, val type: ErrorType) :
        RequestResult<Nothing>()
}