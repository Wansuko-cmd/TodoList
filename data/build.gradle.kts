plugins {
    id(Plugins.androidLibrary)
    id(Plugins.daggerHilt)
}

android {

    defaultConfig {
        buildConfigField("String", "FLAVOR_TYPE", "MOCK".asStringLiteral())
    }

    buildTypes {
        create("mock") {
            buildConfigField("String", "FLAVOR_TYPE", "MOCK".asStringLiteral())
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":utils"))

    implementation(Deps.Kotlin.coroutine)
}