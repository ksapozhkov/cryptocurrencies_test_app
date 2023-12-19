package com.newapp.cryptocurrencytestapp.presentation.elm

import com.newapp.cryptocurrencytestapp.data.ErrorType
import com.newapp.cryptocurrencytestapp.domain.model.CryptocurrencyModel

data class PageState(
    val cryptoCurrencies: List<CryptocurrencyModel>? = null,
    val error: StateError? = null,
    val isLoading: Boolean = false
)

data class StateError(val error: Throwable? = null, val errorType: ErrorType? = null)

sealed class PageEvent {

    sealed class Ui : PageEvent() {
        data object Init : Ui()
        data object ClickReloadScreen : Ui()
    }

    sealed class Internal : PageEvent() {
        data class LoadSuccess(val pages: List<CryptocurrencyModel>) : Internal()
        data class LoadError(val error: Throwable? = null, val errorType: ErrorType? = null) :
            Internal()
    }
}

sealed class PageEffect {
    data class LoadError(val errorType: ErrorType?) : PageEffect()
}

sealed class PageCommand {
    data object GetCryptoCurrencies : PageCommand()
}
