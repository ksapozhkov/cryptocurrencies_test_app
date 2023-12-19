package com.newapp.cryptocurrencytestapp.presentation.elm

import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class PageReducer :
    ScreenDslReducer<PageEvent, PageEvent.Ui, PageEvent.Internal, PageState, PageEffect, PageCommand>(
        PageEvent.Ui::class, PageEvent.Internal::class
    ) {

    override fun Result.ui(event: PageEvent.Ui) = when (event) {
        PageEvent.Ui.Init -> {
            state { copy(isLoading = true) }
            commands {
                +PageCommand.GetCryptoCurrencies
            }
        }

        PageEvent.Ui.ClickReloadScreen -> {
            state { copy(error = null, isLoading = true) }
            commands {
                +PageCommand.GetCryptoCurrencies
            }
        }
    }

    override fun Result.internal(event: PageEvent.Internal) = when (event) {
        is PageEvent.Internal.LoadSuccess -> state {
            copy(cryptoCurrencies = event.pages, isLoading = false, error = null)
        }

        is PageEvent.Internal.LoadError -> {
            state { copy(error = StateError(event.error, event.errorType), isLoading = false) }
            effects { +PageEffect.LoadError(errorType = event.errorType) }
        }
    }

}