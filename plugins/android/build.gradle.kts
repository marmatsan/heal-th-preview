plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.0.2")
    implementation(kotlin("gradle-plugin"))
    implementation(kotlin("android-extensions"))
}

gradlePlugin {
    plugins {
        register("com.marmatsan.android") {
            id = "com.marmatsan.android"
            implementationClass = "com.marmatsan.android.Android"
        }
    }
}