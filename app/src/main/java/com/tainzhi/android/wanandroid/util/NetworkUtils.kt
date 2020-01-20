package com.tainzhi.android.wanandroid.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/20 下午12:14
 * @description:
 **/

class NetworkUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }
    }
}