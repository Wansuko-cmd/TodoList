plugins {
    alias(libs.plugins.compose.compiler) apply false
}

tasks.register<Delete>(name = "clean") {
    delete(rootProject.layout.buildDirectory)
}

subprojects {
    apply(plugin = Plugins.ktlint)
}
