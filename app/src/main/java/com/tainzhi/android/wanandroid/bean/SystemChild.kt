package com.tainzhi.android.wanandroid.bean

import java.io.Serializable

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/20 上午6:05
 * @description:
 **/

data class SystemChild(val child: List<SystemChild>,
                       val courseId: Int,
                       val id: Int,
                       val name: String,
                       val order: Int,
                       val parentChapterId: Int,
                       val visible: Int): Serializable