package com.newapp.cryptocurrencytestapp.data.mappers

import com.newapp.cryptocurrencytestapp.data.sources.local.entities.CryptocurrencyEntity
import com.newapp.cryptocurrencytestapp.data.sources.remote.dto.CryptocurrencyDto
import com.newapp.cryptocurrencytestapp.domain.model.CryptocurrencyModel

object CryptocurrencyMapper {

    fun CryptocurrencyEntity.asDomainModel(): CryptocurrencyModel {
        return CryptocurrencyModel(
            ticker = ticker,
            name = name,
            icon = icon,
            price = price
        )
    }

    fun CryptocurrencyDto.asDomainModel(): CryptocurrencyModel {
        return CryptocurrencyModel(
            ticker = assetId,
            name = name,
            icon = getIcon(),
            price = priceUsd
        )
    }

    fun CryptocurrencyDto.asEntity(): CryptocurrencyEntity {
        return CryptocurrencyEntity(
            id = 0,
            ticker = assetId,
            name = name,
            icon = getIcon(),
            price = priceUsd
        )
    }

}