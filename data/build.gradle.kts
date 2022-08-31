plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

android {
    flavorDimensions.add("default")
    productFlavors {
        create("mock") {
            buildConfigField("String", "FLAVOR_TYPE", "MOCK".asStringLiteral())
        }
        create("prod") {
            buildConfigField("String", "FLAVOR_TYPE", "PROD".asStringLiteral())
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))

    implementation(Deps.Kotlin.coroutine)
}