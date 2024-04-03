package com.apodacatech.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton
/**
 * Interceptor which adds authorization token in header.
 */
@Singleton
class AuthInterceptor @Inject constructor(
  //  private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = chain.request().newBuilder().apply {
            //sessionManager.getToken()?.let { header("Authorization", "Bearer $it") }
            header("Authorization", "Bearer ${"MyToken"}")
        }.build()
        return chain.proceed(authRequest)
    }
}
