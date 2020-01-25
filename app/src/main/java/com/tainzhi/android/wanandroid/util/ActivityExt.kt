package com.tainzhi.android.wanandroid.util

import android.content.Context
import android.widget.Toast

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/24 下午3:02
 * @description:
 **/

fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, content, duration).apply { show() }
}

