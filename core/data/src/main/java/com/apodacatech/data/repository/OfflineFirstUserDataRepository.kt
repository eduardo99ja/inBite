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

import com.apodacatech.model.data.DarkThemeConfig
import com.apodacatech.model.data.ThemeBrand
import com.apodacatech.model.data.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class OfflineFirstUserDataRepository @Inject constructor(

): UserDataRepository{
    override val userData: Flow<UserData> = flow {
        //TODO : Implement shared preferences
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