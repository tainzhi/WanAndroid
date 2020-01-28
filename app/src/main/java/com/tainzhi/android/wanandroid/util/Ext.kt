package com.tainzhi.android.wanandroid.util

import android.app.Activity
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.Response
import com.tainzhi.android.wanandroid.bean.SystemChild
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/28 上午6:46
 * @description:
 **/


const val TOOL_URL = "http://www.wanandroid.com/tools"
const val GITHUB_PAGE = "https://github.com/tainzhi/WanAndroid"
const val ISSUE_URL = "https://github.com/lulululbj/wanandroid/issues"

suspend fun executeResponse(response: Response<Any>, successBlock: suspend CoroutineScope.() ->
Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        if (response.errorCode == -1) errorBlock()
        else successBlock()
    }
}

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        toast(R.string.net_error)
        func(e)
    }
}

fun Response<Any>.isSuccess(): Boolean = this.errorCode == 0

fun transFormSystemChild(children: List<SystemChild>): String {
    return children.joinToString("     ", transform = { child -> child.name })
}
