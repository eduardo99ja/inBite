package com.apodacatech.inbite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apodacatech.data.di.RemoteRepository
import com.apodacatech.data.repository.AuthRepository
import com.apodacatech.data.repository.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @RemoteRepository private val authRepository: AuthRepository
) : ViewModel() {

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Log.d("login", "Login called")

            when (val result = authRepository.login(email, password)) {
                is Either.Success -> {
                    Log.d("login", "Success")
                }
                is Either.Error -> {
                    // Handle error
                }
            }
        }
    }
}