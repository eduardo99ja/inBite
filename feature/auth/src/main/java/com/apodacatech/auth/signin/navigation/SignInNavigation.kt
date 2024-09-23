package com.apodacatech.auth.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apodacatech.auth.signin.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
object SignInRoute

fun NavController.navigateToSignIn(navOptions: NavOptions) = navigate(route = SignInRoute, navOptions)

fun NavGraphBuilder.signInScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<SignInRoute> {
        SignInScreen(
            onShowSnackbar = onShowSnackbar,
        )
    }
}