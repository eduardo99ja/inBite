package com.apodacatech.auth.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apodacatech.ui.DevicePreviews
import com.apodacatech.ui.theme.InBiteTheme

@Composable
internal fun SignInScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    SignInContent(modifier)
}

@Composable
internal fun SignInContent(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {

        Column(modifier = modifier) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            Text(
                text = "Hello Eduardooooo",
            )


            Button(onClick = {


            }) {
                Text("Click me")

            }

        }
    }
}

@DevicePreviews
@Composable
fun SignInScreenPreview() {
    InBiteTheme(
        darkTheme = false
    ) {
        SignInContent()
    }
}