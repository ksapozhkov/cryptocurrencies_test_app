package com.newapp.cryptocurrencytestapp.data.sources.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptocurrencyDto(

    @Json(name = "asset_id")
    val assetId: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "type_is_crypto")
    val typeIsCrypto: Int,

    @Json(name = "data_quote_start")
    val dataQuoteStart: String,

    @Json(name = "data_quote_end")
    val dataQuoteEnd: String,

    @Json(name = "data_orderbook_start")
    val dataOrderBookStart: String,

    @Json(name = "data_orderbook_end")
    val dataOrderBookEnd: String,

    @Json(name = "data_trade_start")
    val dataTradeStart: String,

    @Json(name = "data_trade_end")
    val dataTradeEnd: String,

    @Json(name = "data_symbols_count")
    val dataSymbolsCount: Int,

    @Json(name = "volume_1hrs_usd")
    val volume1hrsUsd: Double,

    @Json(name = "volume_1day_usd")
    val volume1dayUsd: Double,

    @Json(name = "volume_1mth_usd")
    val volume1mthUsd: Double,

    @Json(name = "price_usd")
    val priceUsd: Double,

    @Json(name = "id_icon")
    val idIcon: String,

    @Json(name = "data_start")
    val dataStart: String,

    @Json(name = "data_end")
    val dataEnd: String

) {

    fun getIcon(): String {
        return "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_128/" + idIcon.replace(
            "-",
            ""
        ) + ".png"
    }

}