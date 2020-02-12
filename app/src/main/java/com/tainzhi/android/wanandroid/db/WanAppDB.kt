package com.tainzhi.android.wanandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tainzhi.android.wanandroid.bean.User

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/12 下午11:39
 * @description:
 **/


@Database(
        entities = [
            User::class,
            Repo::class,
            Contributor::class,
            RepoSearchResult::class],
        version = 1,
        exportSchema =  false
)
abstract class WanAppDB : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
