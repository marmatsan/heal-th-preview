import com.marmatsan.dependencies.Dependencies.Env

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("./plugins")
}

plugins {
    id("com.marmatsan.dependencies") apply true
}

rootProject.name = "Heal-th-Preview"

include(
    Env.Modules.Identifiers.app,
    *arrayOf(
        Env.Modules.Identifiers.coreData,
        Env.Modules.Identifiers.coreDomain,
        Env.Modules.Identifiers.coreUi,
    ),
    *arrayOf(
        Env.Modules.Identifiers.onboardingDomain,
        Env.Modules.Identifiers.onboardingUi
    ),
    *arrayOf(
        Env.Modules.Identifiers.trackerData,
        Env.Modules.Identifiers.trackerDomain,
        Env.Modules.Identifiers.trackerUi
    )
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}