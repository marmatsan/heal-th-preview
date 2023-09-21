package com.marmatsan.compose

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class Compose : Plugin<Project> {
    override fun apply(project: Project) {

        val androidExtension = when {
            project.plugins.hasPlugin(AppPlugin::class.java) -> {
                project.extensions.getByName("android") as ApplicationExtension
            }

            else -> {
                project.extensions.getByName("android") as LibraryExtension
            }
        }

        androidExtension.apply {

            defaultConfig {
                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.0"
            }

        }

        val composeVersion = "1.4.3"

        project.dependencies {
            add("implementation", "androidx.compose.animation:animation:$composeVersion")
            add("implementation", "androidx.compose.foundation:foundation:$composeVersion")
            add("implementation", "androidx.compose.material3:material3:1.1.1")
            add("implementation", "androidx.compose.runtime:runtime:$composeVersion")
            add("implementation", "androidx.compose.compiler:compiler:1.5.0")
            add("implementation", "androidx.navigation:navigation-compose:2.6.0")
            add("implementation", "androidx.hilt:hilt-navigation-compose:1.0.0")
            add("implementation", "androidx.activity:activity-compose:1.7.2")
            add("implementation", "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

            // UI
            add("implementation", "androidx.compose.ui:ui:$composeVersion")
            add("implementation", "androidx.compose.ui:ui-tooling:$composeVersion")
            add("implementation", "androidx.compose.ui:ui-geometry:$composeVersion")
            add("implementation", "androidx.compose.ui:ui-graphics:$composeVersion")
            add("implementation", "androidx.compose.ui:ui-unit:$composeVersion")
        }

    }
}