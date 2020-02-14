package com.tainzhi.android.wanandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.bean.SearchHistory

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/12 下午11:39
 * @description:
 **/


@Database(
        entities = arrayOf(
                SearchHistory::class,
                BrowseHistory::class),
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WanAppDB : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
