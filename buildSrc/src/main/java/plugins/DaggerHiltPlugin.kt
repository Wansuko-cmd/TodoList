package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DaggerHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                add("implementation", Deps.Hilt.hiltAndroid)
                add("kapt", Deps.Hilt.hiltCompiler)
            }
        }
    }
}