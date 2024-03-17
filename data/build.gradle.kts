plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":utils"))

    implementation(libs.kotlin.coroutine)
    implementation(libs.kotlin.datetime)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
}
