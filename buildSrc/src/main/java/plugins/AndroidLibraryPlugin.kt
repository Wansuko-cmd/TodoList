package plugins

import Android
import com.android.build.gradle.LibraryExtension
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

                configureCommonAndroidSetting()

                defaultConfig {
                    targetSdk = Android.targetSdk
                }

                buildTypes {
                    create("mock") {
                        signingConfig = signingConfigs.getByName("debug")
                    }
                }
            }
        }
    }
}
