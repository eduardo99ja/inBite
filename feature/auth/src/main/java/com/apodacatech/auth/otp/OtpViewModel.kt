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

package com.apodacatech.auth.otp

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.apodacatech.auth.otp.navigation.OtpRoute
import com.apodacatech.data.di.RemoteRepository
import com.apodacatech.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @RemoteRepository private val authRepository: AuthRepository
) : ViewModel() {

    val phoneNumber = savedStateHandle.toRoute<OtpRoute>().phoneNumber

    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var countdownJob: Job? = null

    init {
        startCountdown()
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
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
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