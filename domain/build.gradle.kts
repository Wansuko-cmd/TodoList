plugins {
    id(Plugins.androidLibrary)
}

android {
    namespace = "com.wsr.domain"
}

dependencies {
    implementation(project(":utils"))
    implementation(libs.kotlin.coroutine)
    implementation(libs.kotlin.datetime)

    testImplementation(libs.bundles.test)
}
