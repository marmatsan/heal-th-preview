plugins {
    alias(plugins.plugins.org.jetbrains.kotlin.plugin.serialization) apply true
}

dependencies {
    // Kotlin Serialization
    implementation(libs.org.jetbrains.kotlinx.serialization)
}