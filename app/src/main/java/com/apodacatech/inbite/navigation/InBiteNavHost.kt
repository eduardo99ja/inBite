/*
 * Copyright (C) 2024 Eduardo Apodaca
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

package com.apodacatech.inbite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.apodacatech.auth.otp.navigation.navigateToOtp
import com.apodacatech.auth.otp.navigation.otpScreen
import com.apodacatech.auth.signin.navigation.SignInRoute
import com.apodacatech.auth.signin.navigation.signInScreen
import com.apodacatech.auth.signup.navigation.navigateToSignUp
import com.apodacatech.auth.signup.navigation.signUpScreen
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
        signInScreen(onShowSnackBar, onNavigateToRegister = navController::navigateToSignUp)
        signUpScreen(onShowSnackbar = onShowSnackBar,
            onNavigateToOtp = navController::navigateToOtp,
            onBackClick = {
                navController.popBackStack()
            })
        otpScreen(onBackClick = {
            navController.popBackStack()
        })

    }
}