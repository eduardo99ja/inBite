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

package com.apodacatech.network.model.response


import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
//    @SerializedName("email")
//    val email: Any,
//    @SerializedName("email_verified")
//    val emailVerified: Boolean,
//    @SerializedName("first_name")
//    val firstName: Any,
//    @SerializedName("id")
//    val id: Int,
//    @SerializedName("inserted_at")
//    val insertedAt: String,
//    @SerializedName("is_active")
//    val isActive: Boolean,
//    @SerializedName("last_name")
//    val lastName: Any,
//    @SerializedName("middle_name")
//    val middleName: Any,
//    @SerializedName("otp_code")
//    val otpCode: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
//    @SerializedName("phone_verified")
//    val phoneVerified: Boolean,
//    @SerializedName("profile_image")
//    val profileImage: Any,
//    @SerializedName("provider")
//    val provider: String,
//    @SerializedName("roles")
//    val roles: List<Any>,
    @SerializedName("token")
    val token: String,
//    @SerializedName("update_at")
//    val updateAt: String
)