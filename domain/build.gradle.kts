plugins {
    id(Plugins.androidLibrary)
}

dependencies {
    implementation(project(":utils"))

    implementation(Deps.Kotlin.coroutine)

    testImplementation(Deps.Test.truth)
}