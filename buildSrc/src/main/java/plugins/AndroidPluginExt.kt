package plugins

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion

fun CommonExtension<*, *, *, *>.configureCommonAndroidSetting() {
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
