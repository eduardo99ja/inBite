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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.apodacatech.component.InBiteBackground
import com.apodacatech.component.InBiteGradientBackground
import com.apodacatech.inbite.R
import com.apodacatech.inbite.navigation.InBiteNavHost
import com.apodacatech.inbite.navigation.NavigationState
import com.apodacatech.inbite.navigation.SearchRoute
import com.apodacatech.inbite.navigation.TOP_LEVEL_NAV_ITEMS
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


    val navigationState = appState.navigationState
    val currentRoute by remember(navigationState) {
        derivedStateOf {
            val currentStack = navigationState.backStacks[navigationState.topLevelRoute]
            currentStack?.lastOrNull() ?: navigationState.topLevelRoute
        }
    }
    val shouldShowBottomBar by remember(navigationState) {
        derivedStateOf { currentRoute in TOP_LEVEL_NAV_ITEMS.keys }
    }

    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (shouldShowBottomBar) {
                UberEatsBottomBar(
                    navigationState = navigationState,
                    onNavigate = { appState.navigator.navigate(it) }
                )
            }
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

@Composable
fun UberEatsBottomBar(
    navigationState: NavigationState,
    onNavigate: (NavKey) -> Unit
) {
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(72.dp),
        shape = CircleShape,
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
                val isSelected = navKey == navigationState.topLevelRoute

                if (navKey == SearchRoute) {
                    // Center Search Bar
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .padding(horizontal = 8.dp)
                            .clickable { onNavigate(navKey) },
                        shape = CircleShape,
                        color = Color(0xFFF6F6F6),
                        border = BorderStroke(0.5.dp, Color.LightGray.copy(alpha = 0.3f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Buscar",
                                color = Color.Gray,
                                fontSize = 15.sp
                            )
                        }
                    }
                } else {
                    // Regular Navigation Icon
                    IconButton(
                        onClick = { onNavigate(navKey) },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.iconTextId,
                            tint = if (isSelected) Color.Black else Color.Gray.copy(alpha = 0.7f),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }
    }
}
