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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(

) : ViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var countdownJob: Job? = null

    init {
        startCountdown()
    }

    // Start the countdown from 60 seconds
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
        _uiState.update { it.copy(otpCode = otpCode) }
    }

    fun onEvent(event: OtpEvent) {
        when (event) {
            is OtpEvent.OnOtpTextChange -> {
                setOtpCode(event.otpValue)
            }
            is OtpEvent.OnResendCode ->{
                // Todo : Resend otp code
                startCountdown()
            }
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
    val countdown: Int = 60
)