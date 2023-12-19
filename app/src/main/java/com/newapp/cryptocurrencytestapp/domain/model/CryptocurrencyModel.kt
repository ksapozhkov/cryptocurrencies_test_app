package com.newapp.cryptocurrencytestapp.domain.model

data class CryptocurrencyModel(
    val ticker: String,
    val name: String,
    val icon: String,
    var price: Double
)