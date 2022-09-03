package plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
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

                configureCommonAndroidSetting()

                defaultConfig {
                    applicationId = Android.applicationId
                    versionCode = Android.versionCode
                    versionName = Android.versionName
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
