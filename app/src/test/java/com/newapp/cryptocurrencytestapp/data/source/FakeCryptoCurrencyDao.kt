package com.newapp.cryptocurrencytestapp.data.source

import com.newapp.cryptocurrencytestapp.data.sources.local.db.dao.CryptocurrencyDao
import com.newapp.cryptocurrencytestapp.data.sources.local.entities.CryptocurrencyEntity

class FakeCryptoCurrencyDao() : CryptocurrencyDao {

    private var entities = ArrayList<CryptocurrencyEntity>()

    override suspend fun getAll(): List<CryptocurrencyEntity> {
        return entities
    }

    override suspend fun deleteAll() {
        entities.clear()
    }

    override suspend fun insertAll(order: List<CryptocurrencyEntity>) {
        entities.clear()
        entities.addAll(order)
    }

}