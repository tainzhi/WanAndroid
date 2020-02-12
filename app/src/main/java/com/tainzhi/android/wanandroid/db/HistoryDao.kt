package com.tainzhi.android.wanandroid.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tainzhi.android.wanandroid.bean.Article

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/12 下午11:32
 * @description:
 **/

@Dao
abstract class HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(article: Article)

    @Query("SELECT * FROM History")
    abstract suspend fun getAll(): LiveData<List<Article>>
}