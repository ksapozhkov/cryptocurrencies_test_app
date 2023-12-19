package com.newapp.cryptocurrencytestapp.domain.usecases

import com.newapp.cryptocurrencytestapp.data.repositories.CryptocurrencyRepository
import com.newapp.cryptocurrencytestapp.domain.model.CryptocurrencyModel
import com.newapp.cryptocurrencytestapp.domain.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptocurrenciesUseCase @Inject constructor(private val cryptoCurrencyRepository: CryptocurrencyRepository) {

    private val predefinedCryptocurrencyList =
        "BTC,ETH,USDT,XRP,ADA,DOGE,TRX,LINK,WBTC,DAI,DOT,LTC,BCH,XMR,NEO"

    fun invoke(): Flow<ResultWrapper<List<CryptocurrencyModel>>> {
        return cryptoCurrencyRepository.getCryptoCurrencies(predefinedCryptocurrencyList)
    }

}