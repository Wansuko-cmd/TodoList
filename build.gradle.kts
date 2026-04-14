plugins {
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
}

tasks.register<Delete>(name = "clean") {
    delete(rootProject.layout.buildDirectory)
}

subprojects {
    apply(plugin = Plugins.ktlint)
}
