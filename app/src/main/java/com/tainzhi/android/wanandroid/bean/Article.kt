package com.tainzhi.android.wanandroid.bean

import java.io.Serializable

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/19 下午9:20
 * @description:
 **/

data class Article(val id: Int,
                   val originId: Int,
                   val title: String,
                   val chapterId: Int,
                   val chapterName: String,
                   val envelopePic: String,
                   val link: String,
                   val author: String,
                   val origin: String,
                   val publishTime: Long,
                   val zan: Int,
                   val desc: String,
                   val visible: Int,
                   val niceDate: String,
                   val niceShareDate: String,
                   val courseId: Int,
                   var collect: Boolean,
                   val apkLink: String,
                   val projectLink: String,
                   val superChapterId: Int,
                   val superChapterName: String?,
                   val type: Int,
                   val fresh: Boolean,
                   val audit: Int,
                   val prefix: String,
                   val selfVisible: Int,
                   val shareDate: Long,
                   val shareUser: String,
                   val userId: Int
) : Serializable