package com.apodacatech.data.repository

import arrow.core.Either
import com.apodacatech.network.model.response.LoginResponse
import javax.inject.Singleton

@Singleton
interface AuthRepository {
    suspend fun login(email: String, password: String): Either<String, LoginResponse>

}