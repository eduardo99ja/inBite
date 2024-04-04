package com.apodacatech.inbite


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.apodacatech.data.di.RemoteRepository
import com.apodacatech.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @RemoteRepository private val authRepository: AuthRepository
) : ViewModel() {

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