plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.android)
    implementation(libs.gradle.ksp)
    implementation(libs.gradle.hilt)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.wsr.android.application"
            implementationClass = "plugins.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "com.wsr.android.library"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("androidCompose") {
            id = "com.wsr.compose"
            implementationClass = "plugins.AndroidComposePlugin"
        }
        register("daggerHilt") {
            id = "com.wsr.android.dagger-hilt"
            implementationClass = "plugins.DaggerHiltPlugin"
        }
        register("ktlint") {
            id = "com.wsr.ktlint"
            implementationClass = "plugins.KtlintPlugin"
        }
    }
}
