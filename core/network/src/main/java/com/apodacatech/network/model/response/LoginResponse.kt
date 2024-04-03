package com.apodacatech.network.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("token")
    val token: String
)