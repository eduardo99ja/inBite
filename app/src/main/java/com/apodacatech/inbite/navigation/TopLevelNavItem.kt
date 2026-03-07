/*
 * Copyright (C) 2026 Eduardo Apodaca
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

package com.apodacatech.inbite.navigation

import androidx.annotation.DrawableRes
import com.apodacatech.home.navigation.HomeRoute
import com.apodacatech.inbite.R

data class TopLevelNavItem(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val iconTextId: String,
    val titleTextId: String,
)

val HOME = TopLevelNavItem(
    selectedIcon = R.drawable.ic_home,
    unselectedIcon = R.drawable.ic_home,
    iconTextId = "Home",
    titleTextId = "Home",
)
val EXPLORE = TopLevelNavItem(
    selectedIcon = R.drawable.ic_home,
    unselectedIcon = R.drawable.ic_home,
    iconTextId = "Explore",
    titleTextId = "Explore",
)

val ACOUNT = TopLevelNavItem(
    selectedIcon = R.drawable.ic_home,
    unselectedIcon = R.drawable.ic_home,
    iconTextId = "Account",
    titleTextId = "Account",
)


val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeRoute to HOME,
//    BookmarksNavKey to BOOKMARKS,
//    InterestsNavKey(null) to INTERESTS,
)