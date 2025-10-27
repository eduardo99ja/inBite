/*
 * Copyright (C) 2025 Eduardo Apodaca
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

package com.apodacatech.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.apodacatech.ui.DevicePreviews
import com.apodacatech.ui.theme.InBiteTheme
import timber.log.Timber


@Composable
internal fun HomeTab(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLoggin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is MainActivityUiState.NotLoggedIn -> {
            Timber.d("User not logged in, navigating to login screen")
            onNavigateToLoggin()

        }

        is MainActivityUiState.Loading -> {
            // You can show a loading indicator here if needed
            Timber.d("Loading user state...")

        }

        is MainActivityUiState.Success -> {
            Timber.d("User is logged in, showing home tab")
            HomeTabContent(
                // uiState = uiState,
                modifier = modifier
            )
        }

    }

}

@Composable
internal fun HomeTabContent(
    // uiState: UiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = "Home Tab")

    }
}

@DevicePreviews
@Composable
private fun HomeTabPreview() {
    InBiteTheme {
        HomeTabContent(
            // uiState = UiState(),
            modifier = Modifier
        )
    }
}