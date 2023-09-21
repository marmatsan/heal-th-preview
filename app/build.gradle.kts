import com.marmatsan.dependencies.Dependencies.Env

android {

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

}

dependencies {

    /* Modules */
    // Core
    implementation(projects.core.coreData)
    implementation(projects.core.coreDomain)
    implementation(projects.core.coreUi)
    // Onboarding
    implementation(projects.onboarding.onboardingDomain)
    implementation(projects.onboarding.onboardingUi)
    //Tracker
    implementation(projects.tracker.trackerData)
    implementation(projects.tracker.trackerDomain)
    implementation(projects.tracker.trackerUi)

    /* Libraries */
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    // Lifecycle
    implementation(libs.bundles.androidx.lifecycle)
    // Activity Compose
    implementation(libs.androidx.activity)
    // Navigation
    implementation(libs.androidx.navigation)
    // DataStore
    implementation(libs.androidx.datastore)
}
