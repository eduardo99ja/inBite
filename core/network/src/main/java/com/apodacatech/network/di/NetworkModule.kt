package com.apodacatech.network.di

import com.apodacatech.network.Constants
import com.apodacatech.network.InBiteService
import com.apodacatech.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val baseRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor

    }

    private val okHttpClientBuilder: OkHttpClient.Builder =
        OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor())

    @Provides
    fun provideInBiteService(authInterceptor: AuthInterceptor): InBiteService {
        return baseRetrofitBuilder
            .client(okHttpClientBuilder.addInterceptor(authInterceptor).build())
            .build()
            .create(InBiteService::class.java)
    }
}