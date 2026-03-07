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

package com.apodacatech.auth.otp

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apodacatech.auth.otp.navigation.OtpRoute
import com.apodacatech.data.di.RemoteRepository
import com.apodacatech.data.repository.AuthRepository
import com.apodacatech.data.util.SecureUserStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber


@HiltViewModel(assistedFactory = OtpViewModel.Factory::class)
class OtpViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    @RemoteRepository private val authRepository: AuthRepository,
    private val secureUserStore: SecureUserStore,
    @Assisted val navKey: OtpRoute
) : ViewModel() {

    val phoneNumber = navKey.phoneNumber

    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var countdownJob: Job? = null

    init {
        startCountdown()
        getTokenFromFlowStore()
    }


    private fun startCountdown() {
        countdownJob?.cancel() // Cancel any ongoing countdown if already running
        countdownJob = viewModelScope.launch {
            for (time in 60 downTo 0) {
                _uiState.update { it.copy(countdown = time) }
                delay(1000L) // Wait for 1 second
            }
        }
    }

    private fun setOtpCode(otpCode: String) {
        _uiState.update { it.copy(otpCode = otpCode, errorState = ErrorState.None) }
    }


    fun onEvent(event: OtpEvent) {
        when (event) {
            is OtpEvent.OnOtpTextChange -> {
                setOtpCode(event.otpValue)
            }

            is OtpEvent.OnResendCode -> {
                // Todo : Resend otp code
                startCountdown()
            }

            is OtpEvent.OnOtpFilled -> {
                validateOtp()
            }
        }
    }


    private fun validateOtp() {
        viewModelScope.launch {
            val result = authRepository.verifyOtp(
                phoneNumber = phoneNumber,
                otpCode = uiState.value.otpCode
            )
            result.fold(
                { messageError ->
                    Timber.d("Otp verification failed - $messageError")

                    _uiState.update {
                        it.copy(
                            errorState = ErrorState.Error(
                                message = messageError
                            )
                        )
                    }
                },
                {
                    Timber.d("Otp verification success - $it")
                    // Save user data to secure storage
                    secureUserStore.saveUser(it.phoneNumber, it.token)
                }
            )
        }
    }


    private fun getTokenFromFlowStore() {
        viewModelScope.launch {
            // secureUserStore.saveUser("Eduardo", "tokeenn12123")
            secureUserStore.tokenFlow.collect { token ->
                Timber.d("Token from flow store: $token")

            }
            // Handle the token as needed, e.g., navigate to the main screen
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            navKey: OtpRoute
        ): OtpViewModel
    }


}

@Immutable
data class UiState(
    val otpCode: String = "",
    val countdown: Int = 60,
    val isLoading: Boolean = false,
    val errorState: ErrorState = ErrorState.None
)

sealed class ErrorState {
    object None : ErrorState()
    data class Error(val message: String) : ErrorState()
}