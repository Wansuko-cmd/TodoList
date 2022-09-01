plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

android {

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Compose.composeVersion
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":utils"))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.lifecycleRuntime)

    implementation(Deps.Compose.ui)
    implementation(Deps.Compose.material)
    implementation(Deps.Compose.icon)
    debugImplementation(Deps.Compose.tooling)
    implementation(Deps.Compose.preview)
    implementation(Deps.Compose.navigation)
    implementation(Deps.Compose.lifecycle)
    implementation(Deps.Compose.hilt)
    implementation(Deps.Compose.constraintLayout)
    implementation(Deps.Compose.coil)
    implementation(Deps.Compose.activity)
    implementation(Deps.Compose.systemUiController)
    implementation(Deps.Compose.pager)

    testImplementation(Deps.Test.junit4)
    androidTestImplementation(Deps.AndroidX.Test.junit)
    androidTestImplementation(Deps.AndroidX.Test.espresso)
    androidTestImplementation(Deps.Compose.Test.junit4)

    debugImplementation(Deps.Compose.Test.uiTooling)
    debugImplementation(Deps.Compose.Test.manifest)
}
