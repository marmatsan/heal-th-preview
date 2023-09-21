import com.marmatsan.dependencies.Dependencies.Env

plugins {
    alias(plugins.plugins.org.jetbrains.kotlin.plugin.serialization) apply true
}

android {
    buildTypes {
        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://world.openfoodfacts.org\""
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    /* Modules */
    implementation(projects.core.coreDomain)
    implementation(projects.tracker.trackerDomain)

    /* Libraries */
    // Ktor
    implementation(libs.bundles.io.ktor)
    // Room
    implementation(libs.bundles.androidx.room)
    kapt(libs.androidx.room.compiler) // TODO: Migrate to KSP
}