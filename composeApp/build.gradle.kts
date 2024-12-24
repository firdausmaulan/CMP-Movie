import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    id("app.cash.sqldelight") version "2.0.2"
}

kotlin {
    androidTarget {
        // Somehow error when adding sqldelight
        // @OptIn(ExperimentalKotlinGradlePluginApi::class)
        // compilerOptions {
        //     jvmTarget.set(JvmTarget.JVM_11)
        // }
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = false
            linkerOpts("-lsqlite3")
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation("app.cash.sqldelight:android-driver:2.0.2")
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation("app.cash.sqldelight:native-driver:2.0.2")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.inspektify.ktor3)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.navigation.compose)
            implementation(libs.multiplatform.settings)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            implementation("io.github.the-best-is-best:kgoogle-map:1.0.1-rc5")
            implementation("io.github.the-best-is-best:klocation:1.0.5")
        }
    }
}

android {
    namespace = "fd.cmp.movie"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "fd.cmp.movie"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// if this code below not auto generated file for us then
// we should invalidate cache and restart gradle
// tips : install sqldelight plugin
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("fd.cmp.movie.data.local.db")
        }
    }
}