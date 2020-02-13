package com.tainzhi.android.wanandroid.db

import androidx.room.*
import com.tainzhi.android.wanandroid.bean.Article

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/13 上午8:05
 * @description:
 **/

@Entity(tableName = "browse_history")
data class HistoryBrowseBean(
        @PrimaryKey(autoGenerate = true) val id: Long,
        @ColumnInfo(name = "browse_time_stamp") val time: Long,
        @Embedded(prefix = "article") val article: Article
//    @Ignore val ignoreSome: String
        )
//) {
//    constructor( stamp: Long, article: Article): this(0, stamp, article, "")
//    constructor( stamp: Long, article: Article, unsed: String): this(0, stamp, article, unsed)
//    constructor(): this(0, 0, null, "")
//}
