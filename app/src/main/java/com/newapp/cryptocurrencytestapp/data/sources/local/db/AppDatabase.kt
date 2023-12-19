package com.newapp.cryptocurrencytestapp.data.sources.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newapp.cryptocurrencytestapp.data.sources.local.db.dao.CryptocurrencyDao
import com.newapp.cryptocurrencytestapp.data.sources.local.entities.CryptocurrencyEntity

@Database(entities = [CryptocurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoCurrencyDao(): CryptocurrencyDao

}