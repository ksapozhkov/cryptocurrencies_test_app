package com.newapp.cryptocurrencytestapp.data.sources.remote.api

import com.newapp.cryptocurrencytestapp.data.sources.remote.dto.CryptocurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptocurrencyApi {

    companion object {
        const val HEADER_API_KEY = "X-CoinAPI-Key"
        const val BASE_SERVER_URL = "https://rest.coinapi.io/v1/"
    }

    /**
     * Get cryptocurrencies info
     * assets_ids -  (ex. BTC,ETH,USDT,XRP,ADA,DOGE,TRX,LINK,WBTC,DAI,DOT,LTC,BCH,XMR,NEO)
     */
    @GET("assets")
    suspend fun getCryptoCurrencies(
        @Query(
            value = "filter_asset_id", encoded = true
        ) assetsIds: String
    ): List<CryptocurrencyDto>

}