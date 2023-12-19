package com.newapp.cryptocurrencytestapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import com.newapp.cryptocurrencytestapp.domain.usecases.GetCryptocurrenciesUseCase
import com.newapp.cryptocurrencytestapp.presentation.elm.PageActor
import com.newapp.cryptocurrencytestapp.presentation.elm.PageEffect
import com.newapp.cryptocurrencytestapp.presentation.elm.PageEvent
import com.newapp.cryptocurrencytestapp.presentation.elm.PageReducer
import com.newapp.cryptocurrencytestapp.presentation.elm.PageState
import com.newapp.cryptocurrencytestapp.presentation.ui.theme.ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import vivid.money.elmslie.compose.ElmComponentFragment
import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

@AndroidEntryPoint
class CryptocurrencyFragment : ElmComponentFragment<PageEvent, PageEffect, PageState>() {

    override val initEvent = PageEvent.Ui.Init

    @Inject
    lateinit var getCryptocurrenciesUseCase: GetCryptocurrenciesUseCase

    override fun createStore() = ElmStoreCompat(
        initialState = PageState(),
        reducer = PageReducer(),
        actor = PageActor(getCryptocurrenciesUseCase)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireActivity()).apply {
        setContent {
            ApplicationTheme {
                Screen()
            }
        }
    }

    @Preview
    @Composable
    private fun Screen() {
        val state by state()
        val effect by effect()
        CryptocurrenciesScreen(
            state = state,
            effect = effect,
            onReloadScreen = { store.accept(PageEvent.Ui.ClickReloadScreen) }
        )
    }

    override fun handleEffect(effect: PageEffect) = when (effect) {
        is PageEffect.LoadError -> Unit // Not ui related effect will be handled here
    }

}