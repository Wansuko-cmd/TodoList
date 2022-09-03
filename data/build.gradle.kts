plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":utils"))

    implementation(Deps.Kotlin.coroutine)
}