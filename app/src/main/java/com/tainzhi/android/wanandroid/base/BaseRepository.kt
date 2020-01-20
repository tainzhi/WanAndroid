package com.tainzhi.android.wanandroid.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
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

    suspend fun <T: Any> safeApiCall(call: suspend () -> Result<T>, errorMsg: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            Result.Error(IOException(errorMsg, e))
        }
    }

    suspend fun <T: Any> executeRespons(response: Response<T>,
                                        successBlock: (suspend CoroutineScope.() -> Unit) ?= null,
                                        errorBlock: (suspend CoroutineScope.() -> Unit) ?= null):
            Result<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                Result.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let{ it()}
                Result.Success(response.data)
            }

        }
    }
}