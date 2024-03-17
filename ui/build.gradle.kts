plugins {
    id(Plugins.androidCompose)
    id(Plugins.daggerHilt)
}

android {
    namespace = "com.wsr.ui"
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":utils"))

    testImplementation(libs.bundles.test)
    testImplementation(libs.bundles.androidx.compose.test)
}
