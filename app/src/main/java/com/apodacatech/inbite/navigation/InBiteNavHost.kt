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

package com.apodacatech.inbite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.apodacatech.auth.otp.OtpScreen
import com.apodacatech.auth.otp.OtpViewModel
import com.apodacatech.auth.otp.navigation.OtpRoute
import com.apodacatech.auth.signin.SignInScreen
import com.apodacatech.auth.signin.navigation.SignInRoute
import com.apodacatech.auth.signup.SignUpScreen
import com.apodacatech.auth.signup.SignUpViewModel
import com.apodacatech.auth.signup.navigation.SignUpRoute
import com.apodacatech.home.HomeTab
import com.apodacatech.home.navigation.HomeRoute
import com.apodacatech.inbite.ui.InBiteAppState
import timber.log.Timber

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
    val navigationState = appState.navigationState
    val navigator = appState.navigator

    val entryProvider = entryProvider {
        entry<SignInRoute> {
            SignInScreen(
                onShowSnackbar = onShowSnackBar,
                onNavigateToRegister = { idToken, name ->
                    Timber.d("Navigating to SignUp with idToken: $idToken and name: $name")
                    navigator.navigate(SignUpRoute(idToken, name))
                },
                onNavigateToOtp = { phoneNumber ->
                    navigator.navigate(OtpRoute(phoneNumber))
                }
            )
        }
        entry<SignUpRoute> { key ->


            val viewModel = hiltViewModel<SignUpViewModel, SignUpViewModel.Factory>(
                // Note: We need a new ViewModel for every new RouteB instance. Usually
                // we would need to supply a `key` String that is unique to the
                // instance, however, the ViewModelStoreNavEntryDecorator (supplied
                // above) does this for us, using `NavEntry.contentKey` to uniquely
                // identify the viewModel.
                //
                // tl;dr: Make sure you use rememberViewModelStoreNavEntryDecorator()
                // if you want a new ViewModel for each new navigation key instance.
                creationCallback = { factory ->
                    factory.create(
                        navKey = key
                    )
                }
            )
            SignUpScreen(
                onShowSnackbar = onShowSnackBar,
                onNavigateToOtp = { phoneNumber ->
                    navigator.navigate(OtpRoute(phoneNumber))
                },
                onBackClick = {
                    navigator.goBack()
                },
                viewModel = viewModel


            )
        }
        entry<OtpRoute> { key ->
            val viewModel = hiltViewModel<OtpViewModel, OtpViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(
                        navKey = key
                    )
                }
            )
            OtpScreen(
                onShowSnackbar = onShowSnackBar,
                onBackClick = {
                    navigator.goBack()
                },
                viewModel = viewModel
            )
        }
        entry<HomeRoute> {
            HomeTab(
                onShowSnackbar = onShowSnackBar,
                onNavigateToLoggin = {
                    navigator.navigate(SignInRoute)
                }
            )
        }
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() },
        modifier = modifier
    )
}
