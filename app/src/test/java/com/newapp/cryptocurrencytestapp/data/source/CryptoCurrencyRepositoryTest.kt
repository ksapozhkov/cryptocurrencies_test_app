package com.newapp.cryptocurrencytestapp.data.source

import com.newapp.cryptocurrencytestapp.data.repositories.CryptocurrencyRepositoryImpl
import com.newapp.cryptocurrencytestapp.data.sources.remote.dto.CryptocurrencyDto
import com.newapp.cryptocurrencytestapp.domain.model.CryptocurrencyModel
import com.newapp.cryptocurrencytestapp.domain.model.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CryptoCurrencyRepositoryTest {

    // Class under test
    private lateinit var cryptoCurrencyRepository: CryptocurrencyRepositoryImpl

    private val remoteCryptoCurrencies = getTestRemoteCryptoCurrencies()

    // Test dependencies
    private lateinit var networkDataSource: FakeCryptoCurrencyApi
    private lateinit var localDataSource: FakeCryptoCurrencyDao

    @OptIn(ExperimentalCoroutinesApi::class)
    private var testDispatcher = StandardTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        networkDataSource = FakeCryptoCurrencyApi(remoteCryptoCurrencies.toMutableList())
        localDataSource = FakeCryptoCurrencyDao()
        cryptoCurrencyRepository = CryptocurrencyRepositoryImpl(
            cryptoCurrencyRemoteSource = networkDataSource,
            cryptoCurrencyLocalSource = localDataSource,
            dispatcher = testDispatcher
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getCryptoCurrencies() = testScope.runTest {
        val flow: Flow<ResultWrapper<List<CryptocurrencyModel>>> =
            cryptoCurrencyRepository.getCryptoCurrencies("BTC,LTC")
        Assert.assertEquals((flow.first() as ResultWrapper.Success).value.size, 2)
    }

    private fun getTestRemoteCryptoCurrencies(): List<CryptocurrencyDto> {
        val dto1 = CryptocurrencyDto(
            assetId = "BTC",
            name = "Bitcoin",
            typeIsCrypto = 1,
            dataQuoteStart = "2014-02-24T00:00:00.0000000Z",
            dataQuoteEnd = "2023-10-27T00:00:00.0000000Z",
            dataOrderBookStart = "2014-02-24T17:43:05.0000000Z",
            dataOrderBookEnd = "2023-07-07T00:00:00.0000000Z",
            dataTradeStart = "2010-07-17T00:00:00.0000000Z",
            dataTradeEnd = "2023-10-27T00:00:00.0000000Z",
            dataSymbolsCount = 177616,
            volume1hrsUsd = 311619216462204879.18,
            volume1dayUsd = 12273969516158673999.92,
            volume1mthUsd = 544486209559471030476.40,
            priceUsd = 34114.757794092290967467001538,
            idIcon = "4caf2b16-a017-4e26-a348-2cea69c34cba",
            dataStart = "2010-07-17",
            dataEnd = "2023-10-27"
        )
        val dto2 = CryptocurrencyDto(
            assetId = "LTC",
            name = "Litecoin",
            typeIsCrypto = 1,
            dataQuoteStart = "2014-04-21T00:00:00.0000000Z",
            dataQuoteEnd = "2023-10-27T00:00:00.0000000Z",
            dataOrderBookStart = "2014-04-20T15:06:34.0000000Z",
            dataOrderBookEnd = "2023-07-07T00:00:00.0000000Z",
            dataTradeStart = "2013-05-19T00:00:00.0000000Z",
            dataTradeEnd = "2023-10-27T00:00:00.0000000Z",
            dataSymbolsCount = 5153,
            volume1hrsUsd = 360169875.14,
            volume1dayUsd = 21926220678.01,
            volume1mthUsd = 56997967630963.95,
            priceUsd = 68.012944459487024335436223846,
            idIcon = "a201762f-1499-41ef-9b84-e0742cd00e48",
            dataStart = "2013-05-19",
            dataEnd = "2023-10-27"
        )
        return listOf(dto1, dto2)
    }

}