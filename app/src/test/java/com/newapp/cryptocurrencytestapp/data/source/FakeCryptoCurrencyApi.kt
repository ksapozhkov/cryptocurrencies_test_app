package com.newapp.cryptocurrencytestapp.data.source

import com.newapp.cryptocurrencytestapp.data.sources.remote.api.CryptocurrencyApi
import com.newapp.cryptocurrencytestapp.data.sources.remote.dto.CryptocurrencyDto

class FakeCryptoCurrencyApi(val toMutableList: MutableList<CryptocurrencyDto>) : CryptocurrencyApi {

    override suspend fun getCryptoCurrencies(assetsIds: String): List<CryptocurrencyDto> {
        return toMutableList.filter { assetsIds.contains(it.assetId) }
    }

}