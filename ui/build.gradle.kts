plugins {
    id(Plugins.androidCompose)
    id(Plugins.daggerHilt)
    alias(libs.plugins.compose.compiler)
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
