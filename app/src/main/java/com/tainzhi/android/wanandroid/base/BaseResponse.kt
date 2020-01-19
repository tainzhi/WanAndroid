package com.tainzhi.android.wanandroid.base

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/19 下午9:18
 * @description:
 **/

data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T ) {
}