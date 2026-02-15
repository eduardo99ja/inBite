/*
 * Copyright (C) 2026 Eduardo Apodaca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apodacatech.inbite.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import com.apodacatech.component.InBiteBackground
import com.apodacatech.component.InBiteGradientBackground
import com.apodacatech.inbite.R
import com.apodacatech.inbite.navigation.InBiteNavHost
import com.apodacatech.ui.theme.GradientColors
import com.apodacatech.ui.theme.LocalGradientColors

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


            //show InBiteApp only when we know if the user is logged in or not

            InBiteApp(
                appState = appState,
                snackbarHostState = snackbarHostState,
            )


        }
    }
}


@Composable
internal fun InBiteApp(
    appState: InBiteAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {

    // Read current route from the nav controller as a top-level state in this composable.
    // We do this outside the Scaffold so we can conditionally pass `bottomBar = null`
    // to the Scaffold when the bottom bar should be hidden. Passing `null` prevents
    // the Scaffold from reserving space for the bottom bar (an empty lambda can still
    // leave a visible area on some devices/themes).
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isAuthRoute = currentRoute?.let { route ->
        route.contains("SignInRoute") || route.contains("SignUpRoute") || route.contains("OtpRoute") ||
                route.contains("com.apodacatech.auth")
    } == true

    val shouldShowBottomBar = currentRoute != null && bottomNavItems.any { it.route == currentRoute } && !isAuthRoute

    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // Provide a single @Composable lambda to Scaffold.bottomBar and conditionally
        // render the NavigationBar inside it. This avoids mixing nullable and
        // lambda types which can lead to "Nothing?" type mismatch errors.
        bottomBar = {
//            if (shouldShowBottomBar) {
//                NavigationBar {
//                    bottomNavItems.forEach { item ->
//                        NavigationBarItem(
//                            selected = currentRoute == item.route,
//                            onClick = { appState.navController.navigate(item.route) },
//                            icon = { Icon(item.icon, contentDescription = item.name) },
//                            label = { Text(item.name) },
//                        )
//                    }
//                }
//            }
        }
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
            val shouldShowTopAppBar = false


            Box(

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
                    //modifier = Modifier.fillMaxSize(),
                )

            }
        }

    }

}
