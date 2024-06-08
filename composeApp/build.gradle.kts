import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.sqlDelight) // Need the sqldelight plugin here as well
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
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
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Dependency Injection
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.room.runtime.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //Navigation
            implementation(libs.navigation.compose)

            implementation(libs.koin.compose)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(project(":mvi"))
            implementation(project(":core:domain"))
            implementation(project(":core:data"))
            implementation(project(":core:presentation"))
            implementation(project(":categories:data"))
            implementation(project(":categories:domain"))
            implementation(project(":categories:presentation"))
            implementation(project(":luggage:data"))
            implementation(project(":luggage:domain"))
            implementation(project(":luggage:presentation"))
            implementation(project(":trips:data"))
            implementation(project(":trips:domain"))
            implementation(project(":trips:presentation"))
        }
    }
}

android {
    namespace = "dev.juanrincon.respite"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "dev.juanrincon.respite"
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
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
}