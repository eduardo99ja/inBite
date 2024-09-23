package com.apodacatech.inbite.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apodacatech.component.InBiteBackground
import com.apodacatech.component.InBiteGradientBackground
import com.apodacatech.inbite.MainViewModel
import com.apodacatech.inbite.R
import com.apodacatech.inbite.navigation.InBiteNavHost
import com.apodacatech.ui.theme.GradientColors
import com.apodacatech.ui.theme.LocalGradientColors
import timber.log.Timber

@Composable
fun InBiteApp(
    appState: InBiteAppState,
    modifier: Modifier = Modifier
) {
    val shouldShowGradientBackground = true // TODO implement this
    InBiteBackground(
        modifier = modifier
    ) {
        InBiteGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            val isOffline by appState.isOffline.collectAsStateWithLifecycle()
            val notConnectedMessage = stringResource(R.string.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }

            InBiteApp(
                appState = appState,
                snackbarHostState = snackbarHostState,
            )

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun InBiteApp(
    appState: InBiteAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            var shouldShowTopAppBar = false


            Box(
                // Workaround for https://issuetracker.google.com/338478720
                modifier = Modifier.consumeWindowInsets(
                    if (shouldShowTopAppBar) {
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                    } else {
                        WindowInsets(0, 0, 0, 0)
                    },
                ),
            ) {
                InBiteNavHost(
                    appState = appState,
                    onShowSnackBar = { message, actionLabel ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = actionLabel,
                            duration = SnackbarDuration.Short
                        ) == ActionPerformed
                    },
                    modifier = Modifier.fillMaxSize(),
                )

            }
        }

    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    Column(modifier = modifier) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        Text(
            text = "Hello $name!",
        )


        Button(onClick = {
            Timber.tag("login").d("Button clicked")
            viewModel.login("eduardo@gmail.com", "Abc123")
        }) {
            Text("Click me")

        }

    }

}

//Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//    Greeting("Eduardo")
//}