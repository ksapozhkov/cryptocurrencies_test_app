package com.newapp.cryptocurrencytestapp

import com.newapp.cryptocurrencytestapp.data.mappers.CryptocurrencyMapper.asDomainModel
import com.newapp.cryptocurrencytestapp.data.sources.remote.dto.CryptocurrencyDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperUnitTest {

    @Test
    fun cryptoCurrencyDtoToModel() {
        val dto = CryptocurrencyDto(
            "BTC",
            "Bitcoin",
            1,
            "2014-02-24T00:00:00.0000000Z",
            "2023-10-27T00:00:00.0000000Z",
            "2014-02-24T17:43:05.0000000Z",
            "2023-07-07T00:00:00.0000000Z",
            "2010-07-17T00:00:00.0000000Z",
            "2023-10-27T00:00:00.0000000Z",
            177616,
            311619216462204879.18,
            12273969516158673999.92,
            544486209559471030476.40,
            34114.757794092290967467001538,
            "4caf2b16-a017-4e26-a348-2cea69c34cba",
            "2010-07-17",
            "2023-10-27"
        )

        val model = dto.asDomainModel()

        assertEquals(model.ticker, "BTC")
        assertEquals(model.name, "Bitcoin")
        assertEquals(model.price, 34114.757794092290967467001538, 0.0)
        assertEquals(
            model.icon,
            "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_128/4caf2b16a0174e26a3482cea69c34cba.png"
        )
    }

}