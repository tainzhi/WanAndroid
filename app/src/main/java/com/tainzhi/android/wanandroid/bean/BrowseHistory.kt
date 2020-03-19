package com.tainzhi.android.wanandroid.bean

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/13 上午8:05
 * @description:
 **/

@Entity(tableName = "browse_history")
data class BrowseHistory(
        //        @PrimaryKey(autoGenerate = true) val id: Long,
        @PrimaryKey var articleHashCode: Int,
        @ColumnInfo(name = "browse_time_stamp") val date: Date,
        @Embedded(prefix = "article") val article: Article
        //    @Ignore val ignoreSome: String
) : Serializable
//) {
//    constructor( stamp: Long, article: Article): this(0, stamp, article, "")
//    constructor( stamp: Long, article: Article, unsed: String): this(0, stamp, article, unsed)
//    constructor(): this(0, 0, null, "")
//}
