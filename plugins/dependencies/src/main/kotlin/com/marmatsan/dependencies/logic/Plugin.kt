package com.marmatsan.dependencies.logic

open class Plugin(
    override val name: String,
    override val group: String,
    override val version: String
) : Dependency(name, group, version) {

    object Android {
        private const val group = "com.android"
        private const val version = "8.0.0"

        object Application : Plugin(
            name = "$group.application",
            group = "$group.application",
            version = version
        )

        object Library : Plugin(
            name = "$group.library",
            group = "$group.library",
            version = version
        )
    }

    object JetBrains {
        private const val group = "org.jetbrains.kotlin"
        private const val version = "1.9.0"

        object Android : Plugin(
            name = "$group.android",
            group = "$group.android",
            version = version
        )

        object Jvm : Plugin(
            name = "$group.jvm",
            group = "$group.jvm",
            version = version
        )

        object Kapt : Plugin(
            name = "$group.kapt",
            group = "$group.kapt",
            version = version
        )

        object Parcelize : Plugin(
            name = "$group.plugin.parcelize",
            group = "$group.plugin.parcelize",
            version = version
        )

        object Serialization : Plugin(
            name = "$group.plugin.serialization",
            group = "$group.plugin.serialization",
            version = version
        )
    }

    object Google {
        private const val group = "com.google"

        object DaggerHilt : Plugin(
            name = "$group.dagger.hilt.android",
            group = "$group.dagger.hilt.android",
            version = "2.47"
        )

        object Ksp : Plugin(
            name = "$group.devtools.ksp",
            group = "$group.devtools.ksp",
            version = "1.8.22-1.0.11"
        )
    }

}