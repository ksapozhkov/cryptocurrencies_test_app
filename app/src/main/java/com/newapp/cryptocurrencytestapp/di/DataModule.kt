package com.newapp.cryptocurrencytestapp.di

import android.content.Context
import androidx.room.Room
import com.newapp.cryptocurrencytestapp.data.repositories.CryptocurrencyRepository
import com.newapp.cryptocurrencytestapp.data.repositories.CryptocurrencyRepositoryImpl
import com.newapp.cryptocurrencytestapp.data.sources.local.db.AppDatabase
import com.newapp.cryptocurrencytestapp.data.sources.local.db.dao.CryptocurrencyDao
import com.newapp.cryptocurrencytestapp.domain.usecases.GetCryptocurrenciesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindCryptocurrenciesRepository(repository: CryptocurrencyRepositoryImpl): CryptocurrencyRepository

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideGetCryptocurrencyUseCase(repo: CryptocurrencyRepository): GetCryptocurrenciesUseCase =
        GetCryptocurrenciesUseCase(repo)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "Cryptocurrency.db").build()

    @Provides
    fun provideCryptoCurrencyDao(database: AppDatabase): CryptocurrencyDao =
        database.cryptoCurrencyDao()

}