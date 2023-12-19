package com.newapp.cryptocurrencytestapp.data.repositories

import com.newapp.cryptocurrencytestapp.data.mappers.CryptocurrencyMapper.asDomainModel
import com.newapp.cryptocurrencytestapp.data.mappers.CryptocurrencyMapper.asEntity
import com.newapp.cryptocurrencytestapp.data.sources.local.db.dao.CryptocurrencyDao
import com.newapp.cryptocurrencytestapp.data.sources.remote.api.CryptocurrencyApi
import com.newapp.cryptocurrencytestapp.domain.model.CryptocurrencyModel
import com.newapp.cryptocurrencytestapp.domain.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

interface CryptocurrencyRepository : BaseRepository {

    fun getCryptoCurrencies(
        assetsId: String
    ): Flow<ResultWrapper<List<CryptocurrencyModel>>>

}

class CryptocurrencyRepositoryImpl @Inject constructor(
    private val cryptoCurrencyRemoteSource: CryptocurrencyApi,
    private val cryptoCurrencyLocalSource: CryptocurrencyDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CryptocurrencyRepository {

    override fun getCryptoCurrencies(
        assetsId: String
    ): Flow<ResultWrapper<List<CryptocurrencyModel>>> {
        return flow {
            while (true) {
                val local = cryptoCurrencyLocalSource.getAll().map { it.asDomainModel() }
                if (local.isNotEmpty())
                    emit(ResultWrapper.Success(local))
                val result = safeApiCall(dispatcher) {
                    cryptoCurrencyRemoteSource.getCryptoCurrencies(
                        assetsId
                    )
                }
                when (result) {
                    is RequestResult.Success -> {
                        cryptoCurrencyLocalSource.deleteAll()
                        cryptoCurrencyLocalSource.insertAll(result.value.map { it.asEntity() })
                        emit(ResultWrapper.Success(result.value.map { it.asDomainModel() }))
                    }

                    is RequestResult.Error -> {
                        emit(ResultWrapper.Error(result.type))
                    }
                }
                delay(10.seconds)
            }
        }.flowOn(Dispatchers.IO)
    }
}