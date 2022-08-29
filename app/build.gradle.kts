plugins {
    id(Plugins.androidApplication)
    id(Plugins.daggerHilt)
}

android {
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles.add(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFiles.add(file("proguard-rules.pro"))
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

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.lifecycleRuntime)

    testImplementation(Deps.Test.junit4)
}
