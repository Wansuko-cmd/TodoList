plugins {
    id(Plugins.androidLibrary)
}

dependencies {
    implementation(project(":utils"))
    implementation(libs.kotlin.coroutine)
    implementation(libs.kotlin.datetime)

    testImplementation(libs.bundles.test)
}