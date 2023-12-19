package com.newapp.cryptocurrencytestapp.di

import com.newapp.cryptocurrencytestapp.BuildConfig
import com.newapp.cryptocurrencytestapp.data.sources.remote.api.CryptocurrencyApi
import com.newapp.cryptocurrencytestapp.data.sources.remote.api.CryptocurrencyApi.Companion.BASE_SERVER_URL
import com.newapp.cryptocurrencytestapp.data.sources.remote.api.CryptocurrencyApi.Companion.HEADER_API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)

        val loggingLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = loggingLevel
        builder.addInterceptor(CommonHeadersInterceptor())
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    internal class CommonHeadersInterceptor() : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .addHeader(HEADER_API_KEY, /*BuildConfig.COIN_API_KEY*/ "546546")
                .build()
            return chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_SERVER_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideCryptoCurrencyApi(retrofit: Retrofit): CryptocurrencyApi =
        retrofit.create(CryptocurrencyApi::class.java)

}