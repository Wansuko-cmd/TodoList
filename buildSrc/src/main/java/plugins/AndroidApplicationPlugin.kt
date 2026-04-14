package plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                configureCommonAndroidSetting(this)

                buildTypes {
                    create("mock") {
                        signingConfig = signingConfigs.getByName("debug")
                    }
                }
            }
        }
    }
}
