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

package com.apodacatech.data.repository

import arrow.core.Either
import com.apodacatech.network.model.response.LoginResponse
import com.apodacatech.network.model.response.VerifyOtpResponse
import javax.inject.Singleton

@Singleton
interface AuthRepository {
    /**
     * Logs in the user with the given phone number.
     *
     * @param phoneNumber The phone number to log in with.
     * @return An [Either] containing a [String] error message on the left side if the login fails,
     *         or a [LoginResponse] on the right side if the login is successful.
     */
    suspend fun login(phoneNumber: String): Either<String, LoginResponse>

    /**
     * Verifies the OTP (One-Time Password) for the given phone number.
     *
     * @param phoneNumber The phone number to verify the OTP for.
     * @param otpCode The OTP code to verify.
     * @return An [Either] containing a [String] error message on the left side if the verification fails,
     *         or a [VerifyOtpResponse] on the right side if the verification is successful.
     */
    suspend fun verifyOtp(phoneNumber: String, otpCode: String): Either<String, VerifyOtpResponse>
}