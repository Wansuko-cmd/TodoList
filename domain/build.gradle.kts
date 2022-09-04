plugins {
    id(Plugins.androidLibrary)
}

dependencies {
    implementation(project(":utils"))

    implementation(Deps.Kotlin.coroutine)

    implementation(Deps.Kotlin.datetime)

    testImplementation(Deps.Test.truth)
}