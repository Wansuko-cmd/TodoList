package repository

import com.wsr.exception.DomainException
import com.wsr.result.ApiResult
import kotlin.coroutines.cancellation.CancellationException

inline fun <T> runCatchDomainException(block: () -> T): ApiResult<T, DomainException> = try {
    ApiResult.Success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    ApiResult.Failure(DomainException.SystemError("", e))
}