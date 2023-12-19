package com.newapp.cryptocurrencytestapp.data.repositories

import com.newapp.cryptocurrencytestapp.data.ErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface BaseRepository {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): RequestResult<T> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val code = throwable.code()
                        RequestResult.Error(code, throwable, ErrorType.RemoteServiceError)
                    }
                    else -> { // ex SocketTimeoutException
                        RequestResult.Error(0, throwable, ErrorType.NoInternetConnectionError)
                    }
                }
            }
        }
    }

}