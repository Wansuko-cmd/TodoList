plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.ace.c.android.application"
            implementationClass = "plugins.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "com.ace.c.android.library"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("daggerHilt") {
            id = "com.ace.c.android.dagger-hilt"
            implementationClass = "plugins.DaggerHiltPlugin"
        }
    }
}
