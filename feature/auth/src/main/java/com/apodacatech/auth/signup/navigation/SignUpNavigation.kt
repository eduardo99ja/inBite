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

package com.apodacatech.auth.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.apodacatech.auth.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRoute(val idToken: String, val name: String)

fun NavController.navigateToSignUp(idToken: String, name: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = SignUpRoute(idToken = idToken, name = name)) {
        navOptions()
    }
}

fun NavGraphBuilder.signUpScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
    onNavigateToOtp: (String) -> Unit
) {
    composable<SignUpRoute> {
        SignUpScreen(
            onShowSnackbar = onShowSnackbar,
            onBackClick = onBackClick,
            onNavigateToOtp = onNavigateToOtp
        )
    }
}