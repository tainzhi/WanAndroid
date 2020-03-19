package com.tainzhi.android.wanandroid.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import java.io.IOException

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/19 下午9:22
 * @description:
 **/

open class BaseRepository {
    suspend fun <T: Any> apiCall(call: suspend () -> Response<T> ): Response<T> {
        return call.invoke()
    }

    suspend fun <T: Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String):
            Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            Timber.e("qfq, safeApiCall(${e.toString()}")
            Result.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T: Any> executeResponse(response: Response<T>,
                                        successBlock: (suspend CoroutineScope.() -> Unit) ?= null,
                                        errorBlock: (suspend CoroutineScope.() -> Unit) ?= null):
            Result<T> {
        return coroutineScope {
            Timber.e("qfq, response=${response.toString()}")
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                Timber.e("qfq, Error:errorCode=${response.errorCode}")
                Result.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let{ it()}
                Timber.e("qfq, Success:errorCode=${response.errorCode}")
                Result.Success(response.data)
            }

        }
    }
}