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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.apodacatech.home.navigation.HomeRoute
import kotlinx.serialization.Serializable

data class TopLevelNavItem(
    val icon: ImageVector,
    val iconTextId: String,
    val titleTextId: String,
)

@Serializable
data object MapRoute : NavKey
@Serializable
data object SearchRoute : NavKey
@Serializable
data object CartRoute : NavKey
@Serializable
data object ProfileRoute : NavKey

val HOME = TopLevelNavItem(
    icon = Icons.Rounded.Home,
    iconTextId = "Home",
    titleTextId = "Inicio",
)
val MAP = TopLevelNavItem(
    icon = Icons.Rounded.LocationOn,
    iconTextId = "Map",
    titleTextId = "Mapa",
)
val SEARCH = TopLevelNavItem(
    icon = Icons.Rounded.Search,
    iconTextId = "Search",
    titleTextId = "Buscar",
)
val CART = TopLevelNavItem(
    icon = Icons.Rounded.ShoppingCart,
    iconTextId = "Cart",
    titleTextId = "Carrito",
)
val PROFILE = TopLevelNavItem(
    icon = Icons.Rounded.Person,
    iconTextId = "Profile",
    titleTextId = "Cuenta",
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeRoute to HOME,
    MapRoute to MAP,
    SearchRoute to SEARCH,
    CartRoute to CART,
    ProfileRoute to PROFILE,
)
