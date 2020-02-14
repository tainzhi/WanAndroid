package com.tainzhi.android.wanandroid.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/13 下午5:37
 * @description:
 **/

@Entity(tableName = "search_history")
data class SearchHistory(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val searchKey: String
)