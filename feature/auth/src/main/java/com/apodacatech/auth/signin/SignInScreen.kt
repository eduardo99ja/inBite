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

package com.apodacatech.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apodacatech.auth.R
import com.apodacatech.component.InBiteTextField
import com.apodacatech.ui.DevicePreviews
import com.apodacatech.ui.theme.InBiteTheme

@Composable
internal fun SignInScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignInContent(
        uiState = uiState,
        onEventHandler = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun SignInContent(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onEventHandler: (SignInScreenEvent) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = scrollState.maxValue) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier =
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            TopGreeting()
            LoginForm(
                phoneNumber = uiState.phoneNumber,
                onPhoneNumberChange = {
                    onEventHandler(SignInScreenEvent.OnPhoneNumberChange(it))
                }
            )
        }
        Column(verticalArrangement = Arrangement.Bottom) {
            InBiteFullWidthButton(
                text = stringResource(R.string.feature_auth_continue),
                onClick = {
                    onEventHandler(SignInScreenEvent.OnLogin("email", "password"))
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)

            )
        }


    }

}

@Composable
private fun TopGreeting() {
    Column(Modifier.fillMaxWidth()) {
        Image(
            contentDescription = "App Logo",
            painter = painterResource(id = R.drawable.logo_app),
            modifier = Modifier
                .padding(top = 60.dp)
                .requiredSize(92.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = stringResource(R.string.feature_auth_write_your_phone_number),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 35.dp, start = 16.dp, end = 16.dp)
        )
        Text(
            text = stringResource(R.string.feature_auth_login_advise),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 15.dp)
        )
    }
}

@Composable
private fun LoginForm(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    PhoneNumberTextField(
        value = phoneNumber,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(MaterialTheme.colorScheme.background),
        onValueChange = onPhoneNumberChange,
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )

}

@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Unspecified,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    InBiteTextField(
        value = value,
        label = stringResource(R.string.feature_auth_add_your_phone_number),
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = { Icon(Icons.Outlined.Phone, "Phone") },
        isError = isError,
        prefix = {
            Text(stringResource(R.string.feature_auth_prefix_phone_number))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions
    )
}


@Composable
fun InBiteFullWidthButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(style = MaterialTheme.typography.bodySmall, color = Color.White, text = text)
    }
}


@DevicePreviews
@Composable
fun SignInScreenPreview() {
    InBiteTheme(
        darkTheme = false
    ) {
        SignInContent(
            uiState = UiState(phoneNumber = "7291001805")
        )
    }
}