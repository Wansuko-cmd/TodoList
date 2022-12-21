plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))
    implementation(libs.kotlin.coroutine)
    implementation(libs.kotlin.datetime)

    testImplementation(libs.bundles.test)
}
