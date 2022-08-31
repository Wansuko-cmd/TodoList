package api

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiFlavor(val value: String) {
    companion object {
        const val PROD = "PROD"
        const val MOCK = "MOCK"
    }
}
