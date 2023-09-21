plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("com.marmatsan.dependencies") {
            id = "com.marmatsan.dependencies"
            implementationClass = "com.marmatsan.dependencies.Dependencies"
        }
    }
}
