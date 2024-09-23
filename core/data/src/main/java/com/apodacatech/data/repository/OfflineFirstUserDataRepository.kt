package com.apodacatech.data.repository

import com.apodacatech.model.data.DarkThemeConfig
import com.apodacatech.model.data.ThemeBrand
import com.apodacatech.model.data.UserData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class OfflineFirstUserDataRepository @Inject constructor(

): UserDataRepository{
    override val userData: Flow<UserData> = flow {
        //TODO : Implement shared preferences
        delay(1000)
        emit(UserData(ThemeBrand.DEFAULT, DarkThemeConfig.LIGHT, useDynamicColor = false, shouldHideOnboarding = true))
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        TODO("Not yet implemented")
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        TODO("Not yet implemented")
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        TODO("Not yet implemented")
    }
}