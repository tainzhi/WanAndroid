package com.tainzhi.android.wanandroid.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/13 下午5:37
 * @description:
 **/

@Entity(tableName = "search_history")
data class SearchHistory(
        @ColumnInfo(name = "search_time_stamp") val date: Date,
        @PrimaryKey val searchKey: String
)