plugins {
    id(Plugins.androidApplication)
    id(Plugins.daggerHilt)
}

android {
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles.add(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFiles.add(file("proguard-rules.pro"))
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":ui"))
    implementation(project(":usecase"))
    implementation(project(":data"))
    implementation(project(":utils"))
    implementation(project(":domain"))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.lifecycleRuntime)

    testImplementation(Deps.Test.junit4)
}
