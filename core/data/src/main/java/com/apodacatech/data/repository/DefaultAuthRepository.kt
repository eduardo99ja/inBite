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

package com.apodacatech.data.repository

import arrow.core.Either
import arrow.core.raise.catch
import com.apodacatech.network.InBiteService
import com.apodacatech.network.model.request.LoginRequest
import com.apodacatech.network.model.response.LoginResponse
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthRepository @Inject internal constructor(
    private val inBiteService: InBiteService
) : AuthRepository {
    override suspend fun login(phoneNumber: String): Either<String, LoginResponse> = catch({
        val loginResponse = inBiteService.login(LoginRequest(phoneNumber = phoneNumber))
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