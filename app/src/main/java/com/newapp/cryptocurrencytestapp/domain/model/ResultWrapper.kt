package com.newapp.cryptocurrencytestapp.domain.model

import com.newapp.cryptocurrencytestapp.data.ErrorType

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val type: ErrorType): ResultWrapper<Nothing>()
}