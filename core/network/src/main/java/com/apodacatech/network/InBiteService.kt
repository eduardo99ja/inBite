package com.apodacatech.network

import com.apodacatech.network.model.request.LoginRequest
import com.apodacatech.network.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InBiteService {
    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}