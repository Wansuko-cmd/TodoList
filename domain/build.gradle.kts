plugins {
    id(Plugins.androidLibrary)
}

dependencies {
    implementation(Deps.Kotlin.coroutine)

    testImplementation(Deps.Test.truth)
}