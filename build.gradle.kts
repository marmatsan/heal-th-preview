import com.marmatsan.dependencies.Dependencies.Env

plugins {
    `version-catalog`
    alias(plugins.plugins.com.android.application) apply false
    alias(plugins.plugins.com.android.library) apply false
    alias(plugins.plugins.org.jetbrains.kotlin.android) apply false
    alias(plugins.plugins.com.google.dagger.hilt.android) apply false
    alias(plugins.plugins.org.jetbrains.kotlin.plugin.serialization) apply false

    /* Custom project plugins */
    id("com.marmatsan.android") apply false
    id("com.marmatsan.compose") apply false
}

subprojects {
    when (project.name) {
        Env.Modules.Names.app -> {
            apply(plugin = "com.android.application")
            apply(plugin = "com.marmatsan.android")
            apply(plugin = "com.marmatsan.compose")
        }

        Env.Modules.Names.coreUi,
        Env.Modules.Names.onboardingUi,
        Env.Modules.Names.trackerUi -> {
            apply(plugin = "com.android.library")
            apply(plugin = "com.marmatsan.android")
            apply(plugin = "com.marmatsan.compose")
        }

        Env.Modules.Names.coreData,
        Env.Modules.Names.coreDomain,
        Env.Modules.Names.onboardingDomain,
        Env.Modules.Names.trackerData,
        Env.Modules.Names.trackerDomain -> {
            apply(plugin = "com.android.library")
            apply(plugin = "com.marmatsan.android")
        }
    }
}

