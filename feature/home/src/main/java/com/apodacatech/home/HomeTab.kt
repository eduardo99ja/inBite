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

package com.apodacatech.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ElectricRickshaw
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.LocalBar
import androidx.compose.material.icons.rounded.LocalGroceryStore
import androidx.compose.material.icons.rounded.LocalPizza
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.apodacatech.ui.DevicePreviews
import com.apodacatech.ui.theme.InBiteTheme
import timber.log.Timber


@Composable
fun HomeTab(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNavigateToLoggin: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is MainActivityUiState.NotLoggedIn -> {
            Timber.d("User not logged in, navigating to login screen")
            onNavigateToLoggin()

        }

        is MainActivityUiState.Loading -> {
            // You can show a loading indicator here if needed
            Timber.d("Loading user state...")

        }

        is MainActivityUiState.Success -> {
            Timber.d("User is logged in, showing home tab")
            HomeTabContent(
                // uiState = uiState,
                modifier = modifier
            )
        }

    }

}

@Composable
internal fun HomeTabContent(
    modifier: Modifier = Modifier,
    // uiState: UiState,
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        stickyHeader(key = "location", contentType = "header") { Location() }

        item(key = "categories", contentType = "row") { Categories() }
        item(key = "filters", contentType = "row") { Filters() }
        item(key = "featured_1", contentType = "carousel") { FeaturedRestaurants() }
        item(key = "offers_1", contentType = "carousel") { TodaysOffers() }
        item(key = "featured_2", contentType = "carousel") { FeaturedRestaurants() }
        item(key = "offers_2", contentType = "carousel") { TodaysOffers() }

    }
}

data class Offer(val title: String, val image: String)

@Composable
fun TodaysOffers() {
    val offers =
        listOf(
            Offer(
                "20% de descuento en tu primer pedido",
                "https://tb-static.uber.com/prod/image-proc/processed_images/678e4138bf14bb689f0bdb8018036b83/5283d81c664b43c5f57a3a186d273063.jpeg"
            ),
            Offer(
                "20% de descuento en tu primer pedido",
                "https://tb-static.uber.com/prod/image-proc/processed_images/678e4138bf14bb689f0bdb8018036b83/5283d81c664b43c5f57a3a186d273063.jpeg"
            ),
            Offer(
                "20% de descuento en tu primer pedido",
                "https://tb-static.uber.com/prod/image-proc/processed_images/678e4138bf14bb689f0bdb8018036b83/5283d81c664b43c5f57a3a186d273063.jpeg"
            ),
            Offer(
                "20% de descuento en tu primer pedido",
                "https://tb-static.uber.com/prod/image-proc/processed_images/678e4138bf14bb689f0bdb8018036b83/5283d81c664b43c5f57a3a186d273063.jpeg"
            ),
        )
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text("Ofertas de hoy", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))

        BoxWithConstraints {
            val cardWidth = maxWidth - 32.dp
            LazyRow(
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(offers, key = { it.title }) {
                    Card(modifier = Modifier.width(cardWidth)) {
                        Column {
                            AsyncImage(
                                model = it.image,
                                contentDescription = it.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(it.title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}

data class Restaurant(val name: String, val image: String, val rating: Double, val reviews: Int, val deliveryTime: Int, val deliveryCost: Int)

@Composable
fun FeaturedRestaurants() {
    val restaurants = listOf<Restaurant>(
        Restaurant(
            "Pizza Place",
            "https://tb-static.uber.com/prod/image-proc/processed_images/80c22959a722ad42b6354bd418a0ef22/5283d81c664b43c5f57a3a186d273063.jpeg",
            4.5,
            120,
            30,
            50
        ),
        Restaurant(
            "Sushi Spot",
            "https://tb-static.uber.com/prod/image-proc/processed_images/80c22959a722ad42b6354bd418a0ef22/5283d81c664b43c5f57a3a186d273063.jpeg",
            4.2,
            80,
            25,
            40
        ),
        Restaurant(
            "Burger Joint",
            "https://tb-static.uber.com/prod/image-proc/processed_images/80c22959a722ad42b6354bd418a0ef22/5283d81c664b43c5f57a3a186d273063.jpeg",
            4.0,
            200,
            20,
            30
        ),
        Restaurant(
            "Taco Stand",
            "https://tb-static.uber.com/prod/image-proc/processed_images/80c22959a722ad42b6354bd418a0ef22/5283d81c664b43c5f57a3a186d273063.jpeg",
            4.8,
            150,
            15,
            20
        ),
        Restaurant(
            "Pasta House",
            "https://tb-static.uber.com/prod/image-proc/processed_images/80c22959a722ad42b6354bd418a0ef22/5283d81c664b43c5f57a3a186d273063.jpeg",
            4.3,
            90,
            35,
            60
        ),

        )

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text("Destacados en Uber Eats", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))

        BoxWithConstraints {
            val cardWidth = maxWidth - maxWidth / 3
            LazyRow(
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(restaurants) {
                    Card(modifier = Modifier.width(cardWidth)) {
                        Column {
                            AsyncImage(
                                model = it.image,
                                contentDescription = it.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(it.name, style = MaterialTheme.typography.titleSmall)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = "Rating")
                                    Text(text = "${it.rating} (${it.reviews}) • ${it.deliveryTime} min")
                                }
                                Text("Costo de envío: MXN${it.deliveryCost}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Filters() {
    val filters = listOf("Para llevar", "Costo", "Costo de envío")
    LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
        items(filters) { filter ->
            var selected by remember { mutableStateOf(false) }
            FilterChip(
                selected = selected,
                onClick = { selected = !selected },
                label = { Text(filter) },
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

data class Category(val name: String, val icon: ImageVector)

@Composable
fun Categories() {
    val categories = listOf(
        Category("Pizza", Icons.Rounded.LocalPizza),
        Category("Sushi", Icons.Rounded.Fastfood),
        Category("Súper", Icons.Rounded.LocalGroceryStore),
        Category("Alcohol", Icons.Rounded.LocalBar),
        Category("Express", Icons.Rounded.ElectricRickshaw),
        Category("Pizza", Icons.Rounded.LocalPizza),
        Category("Sushi", Icons.Rounded.Fastfood),
        Category("Súper", Icons.Rounded.LocalGroceryStore),
        Category("Alcohol", Icons.Rounded.LocalBar),
        Category("Express", Icons.Rounded.ElectricRickshaw),
    )
    LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
        items(categories) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(it.icon, contentDescription = it.name)
                Text(it.name)
            }
        }
    }
}

@Composable
fun Location() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Toluca Centro")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Change location")
            }
            Row {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", modifier = Modifier.padding(end = 16.dp))

            }
        }

    }
}

@DevicePreviews
@Composable
private fun HomeTabPreview() {
    InBiteTheme {
        HomeTabContent(
            // uiState = UiState(),
            modifier = Modifier
        )
    }
}
