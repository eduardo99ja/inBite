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

package com.apodacatech.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apodacatech.auth.R
import com.apodacatech.auth.signin.PhoneNumberTextField
import com.apodacatech.component.InBiteFullWidthButton
import com.apodacatech.ui.DevicePreviews
import com.apodacatech.ui.theme.InBiteTheme

@Composable
internal fun SignUpScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToOtp: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SignUpContent(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onNavigateToOtp = onNavigateToOtp,
        onEventHandler = viewModel::onEvent
    )
}

@Composable
internal fun SignUpContent(
    uiState: UiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToOtp: (String) -> Unit,
    onEventHandler: (SignUpEvent) -> Unit = {},
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
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }

            TopGreeting(userName = uiState.name)
            LoginForm(
                phoneNumber = uiState.phoneNumber,
                onPhoneNumberChange = {
                    onEventHandler(SignUpEvent.OnPhoneNumberChange(it))
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            InBiteFullWidthButton(
                text = stringResource(R.string.feature_auth_continue),
                onClick = {
                    onNavigateToOtp(uiState.phoneNumber)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)

            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )


        }


    }

}

@Composable
private fun TopGreeting(
    userName: String
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.feature_auth_sign_up_title, userName),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 35.dp, start = 16.dp, end = 16.dp)
        )
        Text(
            text = stringResource(R.string.feature_auth_sign_up_subtitle),
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


@DevicePreviews
@Composable
fun SignInScreenPreview() {
    InBiteTheme(
        darkTheme = false
    ) {
        SignUpContent(
            uiState = UiState(phoneNumber = "7291001805"),
            onBackClick = {},
            onNavigateToOtp = {}
        )
    }
}