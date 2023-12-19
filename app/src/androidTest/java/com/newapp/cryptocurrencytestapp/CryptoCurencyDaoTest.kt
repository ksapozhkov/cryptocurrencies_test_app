package com.newapp.cryptocurrencytestapp

import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.newapp.cryptocurrencytestapp.data.sources.local.db.AppDatabase
import com.newapp.cryptocurrencytestapp.data.sources.local.db.dao.CryptocurrencyDao
import com.newapp.cryptocurrencytestapp.data.sources.local.entities.CryptocurrencyEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CryptoCurencyDaoTest {
    private var db: AppDatabase? = null
    private var cryptoCurrencyDao: CryptocurrencyDao? = null

    @Before
    @Throws(Exception::class)
    fun createDb() {
        db = inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        )
            .build()
        cryptoCurrencyDao = db?.cryptoCurrencyDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db!!.close()
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun whenInsertCryptoCurrencyThenReadTheSameOne() {
        runTest {
            val cryptoCurrencyEntity = CryptocurrencyEntity(
                id = 0,
                ticker = "BTC",
                name = "Bitcoin",
                icon = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_128/4caf2b16a0174e26a3482cea69c34cba.png",
                price = 34114.757794092290967467001538
            )
            cryptoCurrencyDao!!.insertAll(listOf(cryptoCurrencyEntity))
            var cryptoCurrencies: List<CryptocurrencyEntity> = cryptoCurrencyDao!!.getAll()
            Assert.assertEquals(1, cryptoCurrencies.size)
            Assert.assertEquals(cryptoCurrencies[0].ticker, "BTC")
            cryptoCurrencyDao!!.deleteAll()
            cryptoCurrencies = cryptoCurrencyDao!!.getAll()
            Assert.assertEquals(0, cryptoCurrencies.size)
        }
    }

}