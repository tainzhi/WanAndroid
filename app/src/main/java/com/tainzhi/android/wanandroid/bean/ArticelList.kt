package com.tainzhi.android.wanandroid.bean

import java.io.Serializable

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/19 下午9:20
 * @description:
 **/

data class ArticleList( val offset: Int,
                        val size: Int,
                        val total: Int,
                        val pageCount: Int,
                        val curPage: Int,
                        val over: Boolean,
                        val datas: List<Article>): Serializable