package com.tainzhi.android.wanandroid.db

import androidx.room.*
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.bean.SearchHistory
import java.util.*

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
    abstract fun insertBrowseHistory(browseHistory: BrowseHistory)

    fun insertBrowseHistory(article: Article) {
        insertBrowseHistory(BrowseHistory(article.hashCode(), Date(), article))
    }

    // Coroutine不支持LiveData
    @Query("SELECT * FROM BROWSE_HISTORY ORDER BY 1 DESC")
    abstract suspend fun getBrowseHistory(): List<BrowseHistory>

    @Query("DELETE FROM BROWSE_HISTORY")
    abstract suspend fun deleteBrowseHistory()


    // Search history
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSearchKey(searchHistory: SearchHistory)

    fun insertSearchKey(searchKey: String) {
        insertSearchKey(SearchHistory(Date(), searchKey))
    }

    @Query("SELECT * FROM SEARCH_HISTORY ORDER BY 1 DESC ")
    abstract suspend fun getSearchHistory(): List<SearchHistory>

    @Query("DELETE FROM SEARCH_HISTORY")
    abstract suspend fun deleteSearchHistory()
}