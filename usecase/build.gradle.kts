plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))
    implementation(Deps.Kotlin.coroutine)

    testImplementation(Deps.Test.truth)
    testImplementation(Deps.Test.mockk)
    testImplementation(Deps.Test.turbine)
}
