package com.tainzhi.android.wanandroid.bean

import java.io.Serializable

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/28 上午10:36
 * @description:
 **/


data class Navigation(
        val articles: List<Article>,
        val cid: Int,
        val name: String) : Serializable
