package plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }

            extensions.configure<BaseAppModuleExtension> {
                compileSdk = Android.compileSdk

                defaultConfig {
                    applicationId = Android.applicationId
                    versionCode = Android.versionCode
                    versionName = Android.versionName
                    minSdk = Android.minSdk
                    targetSdk = Android.targetSdk
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
        }
    }
}