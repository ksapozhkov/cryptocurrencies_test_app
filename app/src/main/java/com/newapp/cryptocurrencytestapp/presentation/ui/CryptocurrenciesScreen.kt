package com.newapp.cryptocurrencytestapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.newapp.cryptocurrencytestapp.R
import com.newapp.cryptocurrencytestapp.data.ErrorType
import com.newapp.cryptocurrencytestapp.domain.model.CryptocurrencyModel
import com.newapp.cryptocurrencytestapp.presentation.elm.PageEffect
import com.newapp.cryptocurrencytestapp.presentation.elm.PageState
import com.newapp.cryptocurrencytestapp.presentation.ui.theme.Typography
import com.newapp.cryptocurrencytestapp.util.Extensions.formatPrice
import vivid.money.elmslie.compose.EffectWithKey

@Composable
fun CryptocurrenciesScreen(
    state: PageState, effect: EffectWithKey<PageEffect>?, onReloadScreen: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) { padding ->
        when {
            state.error != null && state.cryptoCurrencies == null -> ErrorState(
                onReloadScreen, padding
            )

            state.error == null && state.cryptoCurrencies == null -> LoadingScreen()
            else -> List(state.cryptoCurrencies)
        }
        effect?.takeIfInstanceOf<PageEffect.LoadError>()?.key?.let {
            Error(scaffoldState = scaffoldState, key = it, errorType = it.value.errorType)
        }
    }
}

@Composable
fun List(cryptocurrencies: List<CryptocurrencyModel>?) {
    require(cryptocurrencies != null)
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        items(cryptocurrencies) {
            CryptocurrencyItem(
                title = it.name,
                description = it.ticker,
                icon = it.icon,
                value = it.price.formatPrice()
            )
        }
    }
}

@Preview
@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState(
    onReloadScreen: () -> Unit, padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.unable_load_data_error_message))
        Button(onClick = onReloadScreen) {
            Text(text = stringResource(id = R.string.action_reload))
        }
    }
}

@Composable
fun CryptocurrencyItem(
    title: String, description: String, icon: String, value: String
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
    ) {
        Row(Modifier.fillMaxHeight(), verticalAlignment = CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(icon).build(),
                contentDescription = "Cryptocurrency icon",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(44.dp)
            )
            Column(
                Modifier
                    .padding(start = 16.dp)
                    .align(CenterVertically)
            ) {
                Text(text = title, style = Typography.titleLarge)
                Text(text = description, style = Typography.labelMedium)
            }
        }
        Text(text = value, Modifier.align(Alignment.CenterEnd), style = Typography.bodySmall)
    }
}

@Composable
fun Error(
    scaffoldState: ScaffoldState, key: Any, errorType: ErrorType?
) {
    val message = when (errorType) {
        ErrorType.NoInternetConnectionError -> stringResource(id = R.string.no_internet_connection_error)
        ErrorType.RemoteServiceError -> stringResource(id = R.string.remote_service_error)
        null -> stringResource(id = R.string.unknown_error)
    }

    LaunchedEffect(key) {
        scaffoldState.snackbarHostState.showSnackbar(message)
    }
}
