package com.tainzhi.android.wanandroid.db

import androidx.room.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/12 下午11:32
 * @description:
 **/

@Dao
abstract class HistoryDao {
    @Transaction
    open suspend fun deleteAll() {
        deleteBrowseHistory()
        deleteSearchHistory()
    }

    // browse history
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(historyBrowseBean: HistoryBrowseBean)

    // Coroutine不支持LiveData
    @Query("SELECT * FROM BROWSE_HISTORY ORDER BY 1 DESC")
    abstract suspend fun getBrowseHistory(): List<HistoryBrowseBean>

    @Query("DELETE FROM BROWSE_HISTORY")
    abstract suspend fun deleteBrowseHistory()


    // Search history
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSearchKey(historySearchBean: HistorySearchBean)

    @Query("SELECT * FROM SEARCH_HISTORY ORDER BY 1 DESC ")
    abstract suspend fun getSearchHistory(): List<HistorySearchBean>

    @Query("DELETE FROM SEARCH_HISTORY")
    abstract suspend fun deleteSearchHistory()
}