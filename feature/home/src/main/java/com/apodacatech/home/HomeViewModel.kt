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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apodacatech.data.util.SecureUserStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val secureUserStore: SecureUserStore
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = secureUserStore.tokenFlow
        .map { token ->
            if (token.isNullOrBlank()) {
                MainActivityUiState.NotLoggedIn
            } else {
                MainActivityUiState.Success(token)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainActivityUiState.Loading
        )

//    private val _uiState= MutableStateFlow<MainActivityUiState>( MainActivityUiState.Loading)
//    val uiState : StateFlow<MainActivityUiState> = _uiState


//    private fun getTokenFromFlowStore() {
//        viewModelScope.launch {
//            // secureUserStore.saveUser("Eduardo", "tokeenn12123")
//            secureUserStore.tokenFlow.collect { token ->
//                Timber.d("Token from flow store: $token")
//                _uiState.value = if (token.isNullOrBlank()) {
//                    MainActivityUiState.NotLoggedIn
//                } else {
//                    MainActivityUiState.Success(token)
//                }
//            }
//            // Handle the token as needed, e.g., navigate to the main screen
//        }
//    }


}

sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()
    data object NotLoggedIn : MainActivityUiState()
    data class Success(val token: String) : MainActivityUiState()
}
