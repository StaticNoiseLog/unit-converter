// Unit Converter — Gradle settings.
// Configures plugin repositories and project name.

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }
}

rootProject.name = "unit-converter"
