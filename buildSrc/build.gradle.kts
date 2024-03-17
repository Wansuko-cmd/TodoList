plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.android)
    implementation(libs.gradle.hilt)
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
        register("ktlint") {
            id = "com.template.ktlint"
            implementationClass = "plugins.KtlintPlugin"
        }
    }
}
