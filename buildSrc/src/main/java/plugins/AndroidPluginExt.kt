package plugins

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion

fun CommonExtension<*, *, *, *>.configureCommonAndroidSetting() {
    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = Android.minSdk
        testInstrumentationRunner = Android.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
