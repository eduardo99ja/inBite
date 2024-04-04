package com.apodacatech.data.repository

import com.apodacatech.network.InBiteService
import com.apodacatech.network.model.request.LoginRequest
import com.apodacatech.network.model.response.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton
import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import timber.log.Timber

@Singleton
class DefaultAuthRepository @Inject internal constructor(
    private val inBiteService: InBiteService
) : AuthRepository {
    override suspend fun login(email: String, password: String): Either<String, LoginResponse> = catch({
        val loginResponse = inBiteService.login(LoginRequest(email, password))
        Timber.tag("login").d("Response: $loginResponse")
        when (loginResponse.code()) {
            201 -> Either.Right(loginResponse.body()!!)
            401 -> Either.Left(loginResponse.message())
            else -> Either.Left(loginResponse.errorBody().toString())
        }
    }) {
        Timber.tag("login").e("Error caught: $it")
        Either.Left(it.message ?: "Error logging in")

    }
}