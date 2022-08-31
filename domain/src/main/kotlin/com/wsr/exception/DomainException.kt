package com.wsr.exception

sealed class DomainException : Exception() {
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : DomainException()
}
