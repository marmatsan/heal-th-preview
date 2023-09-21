package com.marmatsan.dependencies.logic

open class Library(
    override val name: String,
    override val group: String,
    val artifacts: List<String>,
    override val version: String
) : Dependency(name, group, version) {

    object AndroidX {
        private const val androidx = "androidx"

        object Core {
            private const val group = "$androidx.core"

            object Ktx : Library(
                name = "$group.ktx",
                group = group,
                artifacts = listOf(
                    "core-ktx"
                ),
                version = "1.10.1"
            )

            object SplashScreen : Library(
                name = "$group.splashscreen",
                group = group,
                artifacts = listOf(
                    "core-splashscreen"
                ),
                version = "1.0.1"
            )

        }

        object Navigation : Library(
            name = "$androidx.navigation",
            group = "$androidx.navigation",
            artifacts = listOf(
                "navigation-compose"
            ),
            version = "2.6.0"
        )

        object Hilt : Library(
            name = "$androidx.hilt",
            group = "$androidx.hilt",
            artifacts = listOf(
                "navigation-compose"
            ),
            version = "1.0.0"
        )

        object Activity : Library(
            name = "$androidx.activity",
            group = "$androidx.activity",
            artifacts = listOf(
                "activity-compose"
            ),
            version = "1.7.2"
        )

        object LifeCycle : Library(
            name = "$androidx.lifecycle",
            group = "$androidx.lifecycle",
            artifacts = listOf(
                "lifecycle-runtime-ktx",
                "lifecycle-viewmodel-compose"
            ),
            version = "2.6.1"
        )

        object Datastore : Library(
            name = "$androidx.datastore",
            group = "$androidx.datastore",
            artifacts = listOf(
                "datastore"
            ),
            version = "1.0.0"
        )

        object Room {

            private const val group = "$androidx.room"
            private const val version = "2.5.1"

            object RoomBundle : Library(
                name = group,
                group = group,
                artifacts = listOf(
                    "room-runtime",
                    "room-ktx",
                    "room-paging"
                ),
                version = version
            )

            object Compiler : Library(
                name = "$group.compiler",
                group = group,
                artifacts = listOf(
                    "room-compiler"
                ),
                version = version
            )

        }

    }

    object Jetbrains {
        private const val jetbrains = "org.jetbrains"

        object Serialization : Library(
            name = "$jetbrains.kotlinx.serialization",
            group = "$jetbrains.kotlinx",
            artifacts = listOf(
                "kotlinx-serialization-json"
            ),
            version = "1.5.1"
        )
    }

    object Google {
        private const val google = "com.google"

        object DaggerHilt : Library(
            name = "$google.daggerHilt",
            group = "$google.dagger",
            artifacts = listOf(
                "hilt-android"
            ),
            version = "2.47"
        )

        object DaggerHiltCompiler : Library(
            name = "$google.daggerHiltCompiler",
            group = "$google.dagger",
            artifacts = listOf(
                "hilt-android-compiler"
            ),
            version = "2.47"
        )
    }

    object Coil : Library(
        name = "io.coil-kt",
        group = "io.coil-kt",
        artifacts = listOf(
            "coil-compose"
        ),
        version = "1.3.2"
    )

    object Ktor : Library(
        name = "io.ktor",
        group = "io.ktor",
        artifacts = listOf(
            "ktor-client-core",
            "ktor-client-android",
            "ktor-client-serialization",
            "ktor-client-content-negotiation",
            "ktor-serialization-kotlinx-json",
            "ktor-client-logging"
        ),
        version = "2.3.3"
    )

}