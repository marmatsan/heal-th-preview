package com.marmatsan.android

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

class Android : Plugin<Project> {
    override fun apply(project: Project) {

        project.plugins.apply("org.jetbrains.kotlin.android")
        project.plugins.apply("org.jetbrains.kotlin.kapt")
        project.plugins.apply("com.google.dagger.hilt.android")

        val androidExtension = when {
            project.plugins.hasPlugin(AppPlugin::class.java) -> {
                project.extensions.getByName("android") as ApplicationExtension
            }

            else -> {
                project.extensions.getByName("android") as LibraryExtension
            }
        }

        androidExtension.apply {
            namespace = "com.marmatsan.${project.name}"
            compileSdk = 33

            defaultConfig {
                minSdk = 26

                if (androidExtension is ApplicationExtension) {
                    androidExtension.apply {
                        defaultConfig {

                            val majorVersion = 1
                            val minorVersion = 0
                            val bugfixVersion = 0

                            targetSdk = 33
                            versionCode = majorVersion * 1000 + minorVersion * 100 + bugfixVersion
                            versionName = "${majorVersion}.${minorVersion}.$bugfixVersion"
                        }
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                project.tasks.withType<KotlinJvmCompile>().configureEach {
                    compilerOptions.languageVersion.set(KotlinVersion.KOTLIN_1_8)
                }

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            }

        }

        project.dependencies {
            add("implementation", "com.google.dagger:hilt-android:2.47")
            add("kapt", "com.google.dagger:hilt-android-compiler:2.47")
        }

    }
}