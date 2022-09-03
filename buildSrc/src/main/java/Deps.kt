// Dependencies
object Deps {

    object Kotlin {

        const val version = "1.7.0"

        private const val kotlinCoroutineVersion = "1.6.4"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutineVersion" // ktlint-disable max-line-length
    }

    object Hilt {
        private const val hiltVersion = "2.43.2"

        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.8.0"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"

        object Room {
            private const val roomVersion = "2.4.3"
            const val runtime = "androidx.room:room-runtime:$roomVersion"
            const val compiler = "androidx.room:room-compiler:$roomVersion"
            const val ktx = "androidx.room:room-ktx:$roomVersion"
        }

        object Test {
            const val junit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }

    object Compose {
        const val composeVersion = "1.2.0"
        private const val navigationVersion = "2.5.1"

        const val ui = "androidx.compose.ui:ui:$composeVersion"
        const val material = "androidx.compose.material:material:$composeVersion"
        const val icon = "androidx.compose.material:material-icons-extended:$composeVersion"
        const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01"
        const val hilt = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.1" // ktlint-disable max-line-length
        const val coil = "io.coil-kt:coil-compose:2.1.0"
        const val activity = "androidx.activity:activity-compose:1.3.1"
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:0.23.1" // ktlint-disable max-line-length
        const val pager = "com.google.accompanist:accompanist-pager:0.19.0"

        object Test {
            const val junit4 = "androidx.compose.ui:ui-test-junit4:$composeVersion"
            const val manifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        }
    }

    object Test {
        const val truth = "com.google.truth:truth:1.1.3"
        const val junit4 = "junit:junit:4.13.2"
        const val turbine = "app.cash.turbine:turbine:0.9.0"
        const val mockk = "io.mockk:mockk:1.12.5"
        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Kotlin.version}"
    }
}
