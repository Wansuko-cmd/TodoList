plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":utils"))

    implementation(Deps.Kotlin.coroutine)

    implementation(Deps.AndroidX.Room.runtime)
    annotationProcessor(Deps.AndroidX.Room.compiler)
    kapt(Deps.AndroidX.Room.compiler)
    implementation(Deps.AndroidX.Room.ktx)
}