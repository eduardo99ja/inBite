package com.apodacatech.inbite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.apodacatech.auth.signin.navigation.SignInRoute
import com.apodacatech.auth.signin.navigation.signInScreen
import com.apodacatech.inbite.ui.InBiteAppState

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun InBiteNavHost(
    appState: InBiteAppState,
    onShowSnackBar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = SignInRoute,
        modifier = modifier
    ) {
        signInScreen(onShowSnackBar)
    }
}