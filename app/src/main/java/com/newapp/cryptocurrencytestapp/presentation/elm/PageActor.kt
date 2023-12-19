package com.newapp.cryptocurrencytestapp.presentation.elm

import com.newapp.cryptocurrencytestapp.domain.model.ResultWrapper
import com.newapp.cryptocurrencytestapp.domain.usecases.GetCryptocurrenciesUseCase
import vivid.money.elmslie.core.switcher.Switcher
import vivid.money.elmslie.coroutines.Actor
import vivid.money.elmslie.coroutines.switch

class PageActor(private val getCryptocurrenciesUseCase: GetCryptocurrenciesUseCase) :
    Actor<PageCommand, PageEvent> {

    private val switcher = Switcher()

    override fun execute(command: PageCommand) = when (command) {
        is PageCommand.GetCryptoCurrencies -> {
            switcher.switch { getCryptocurrenciesUseCase.invoke() }
                .mapEvents(
                    {
                        if (it is ResultWrapper.Success) {
                            PageEvent.Internal.LoadSuccess(it.value)
                        } else {
                            val error = it as ResultWrapper.Error
                            PageEvent.Internal.LoadError(errorType = error.type)
                        }
                    },
                    PageEvent.Internal::LoadError
                )
        }
    }

}