package com.apodacatech.data.di

import com.apodacatech.data.repository.AuthRepository
import com.apodacatech.data.repository.DefaultAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @RemoteRepository
    fun bindAuthRepository(authRepositoryImp: DefaultAuthRepository): AuthRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepository

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepository
