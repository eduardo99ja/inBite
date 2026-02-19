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

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    kotlin("plugin.serialization") version "2.0.20"
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.apodacatech.home"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
}
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.compose.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    kapt(libs.hilt.compiler)

    //Timber
    implementation(libs.timber)

    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    implementation(libs.activity.compose)
    implementation(libs.material3.compose)
    implementation(libs.navigation.compose)
    implementation(libs.coroutines.core)
//    implementation(libs.compose.tooling.preview)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.coil)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":core:network"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
}
