package plugins

import Android
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = Android.compileSdk

                defaultConfig {
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