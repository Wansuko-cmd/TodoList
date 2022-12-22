plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
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
