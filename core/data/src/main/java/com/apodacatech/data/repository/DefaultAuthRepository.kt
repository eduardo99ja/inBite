package com.apodacatech.data.repository

import com.apodacatech.network.InBiteService
import com.apodacatech.network.model.request.LoginRequest
import com.apodacatech.network.model.response.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthRepository  @Inject internal constructor(
    private val inBiteService: InBiteService
) : AuthRepository{
    override suspend fun login(email: String, password: String): Either<LoginResponse> {
        return runCatching {
            val loginResponse = inBiteService.login(LoginRequest(email, password))

            when (loginResponse.code() ) {
                200, 201 -> Either.success(loginResponse.body()!!)
                else -> Either.error(loginResponse.errorBody().toString())
            }
        }.getOrDefault(Either.error("Something went wrong!"))
    }
}