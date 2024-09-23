package com.apodacatech.inbite


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apodacatech.data.di.RemoteRepository
import com.apodacatech.data.repository.AuthRepository
import com.apodacatech.data.repository.UserDataRepository
import com.apodacatech.model.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @RemoteRepository private val authRepository: AuthRepository,
    userDataRepository: UserDataRepository,
) : ViewModel() {

    val uiState : StateFlow<MainActivityUiState>  = userDataRepository.userData.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Timber.tag("login").d("Login called")

            val result = authRepository.login(email, password)

            result.fold(
                ifLeft = {
                    Timber.tag("login").d("Error: $it")
                },
                ifRight = {
                    Timber.tag("login").d("Success: $it")
                }
            )


        }
    }


}
sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
