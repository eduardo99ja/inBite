package com.apodacatech.data.di

import com.apodacatech.data.repository.OfflineFirstUserDataRepository
import com.apodacatech.data.repository.UserDataRepository
import com.apodacatech.data.util.ConnectivityManagerNetworkMonitor
import com.apodacatech.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}