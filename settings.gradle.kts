rootProject.name = "Respite"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")
include(":mvi")
include(":core:domain")
include(":core:data")
include(":core:presentation")
include(":categories:data")
include(":categories:domain")
include(":categories:presentation")
include(":luggage:data")
include(":luggage:domain")
include(":luggage:presentation")
include(":trips:data")
include(":trips:domain")
include(":trips:presentation")
