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

import androidx.compose.runtime.Immutable
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apodacatech.data.di.RemoteRepository
import com.apodacatech.data.repository.AuthRepository
import com.apodacatech.data.repository.UserDataRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @RemoteRepository private val authRepository: AuthRepository,
    userDataRepository: UserDataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    // A flow to emit navigation events
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent

    private fun setPhoneNumber(phoneNumber: String) {
        //Validate only 10 digits
        if (phoneNumber.length > 10) return

        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun onEvent(event: SignInScreenEvent) {
        when (event) {
            is SignInScreenEvent.OnLogin -> {
                login()
            }

            is SignInScreenEvent.OnPhoneNumberChange -> {
                setPhoneNumber(event.phoneNumber)
            }

            is SignInScreenEvent.OnLoginWithGoogle -> {
                onSignInWithGoogle(event.credential)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = authRepository.login(uiState.value.phoneNumber)

            result.fold(
                ifLeft = {
                    Timber.tag("login").d("Error: $it")
                    _uiState.update { it.copy(isLoading = false) }
                },
                ifRight = {
                    Timber.tag("login").d("Success: $it")
                    _uiState.update { it.copy(isLoading = false) }
                    _navigationEvent.emit(NavigationEvent.NavigateToOtp(uiState.value.phoneNumber))
                }
            )


        }
    }

    private fun onSignInWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    //TODO : Valdiate user has account or register new account
                    Timber.d("Logged ${googleIdTokenCredential.idToken}")
                    _navigationEvent.emit(
                        NavigationEvent.NavigateToRegister(
                            idToken = googleIdTokenCredential.idToken,
                            name = googleIdTokenCredential.displayName.orEmpty()
                        )
                    )
                } catch (e: GoogleIdTokenParsingException) {
                    Timber.e("Error: $e")
                }

            } else {
                Timber.e("Error - UNEXPECTED_CREDENTIAL")
            }
        }

    }

    sealed class NavigationEvent {
        data class NavigateToRegister(val idToken: String, val name: String) : NavigationEvent()
        data class NavigateToOtp(val phoneNumber: String) : NavigationEvent()
    }
}

@Immutable
data class UiState(
    val phoneNumber: String = "",
    val isLoading: Boolean = false
)