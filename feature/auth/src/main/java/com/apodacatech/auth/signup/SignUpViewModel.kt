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

package com.apodacatech.auth.signup

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.apodacatech.auth.signup.navigation.SignUpRoute
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel(assistedFactory = SignUpViewModel.Factory::class)
class SignUpViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    @Assisted val navKey: SignUpRoute
) : ViewModel() {


    private val _uiState = MutableStateFlow(UiState(name = navKey.name, idToken = navKey.idToken))
    val uiState: StateFlow<UiState> = _uiState

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnPhoneNumberChange -> {
                _uiState.value = _uiState.value.copy(phoneNumber = event.phoneNumber)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            navKey: SignUpRoute
        ): SignUpViewModel
    }
}

@Immutable
data class UiState(
    val phoneNumber: String = "",
    val name: String = "",
    val idToken: String = ""
)
