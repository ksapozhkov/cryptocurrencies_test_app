package com.newapp.cryptocurrencytestapp.data.sources.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newapp.cryptocurrencytestapp.data.sources.local.entities.CryptocurrencyEntity

@Dao
interface CryptocurrencyDao {

    @Query("SELECT * FROM cryptocurrency")
    suspend fun getAll(): List<CryptocurrencyEntity>

    @Query("DELETE FROM cryptocurrency")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(order: List<CryptocurrencyEntity>)

}