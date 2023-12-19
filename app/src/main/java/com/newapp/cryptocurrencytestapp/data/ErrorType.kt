package com.newapp.cryptocurrencytestapp.data

sealed class ErrorType {

    data object NoInternetConnectionError : ErrorType()
    data object RemoteServiceError : ErrorType()

}