package com.tainzhi.android.wanandroid

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.tainzhi.android.wanandroid.bean.SearchHistory
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.db.WanAppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 上午12:13
 * @description:
 **/

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var historyDao: HistoryDao
    private lateinit var db: WanAppDB

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope = TestScope(testCoroutineDispatcher)

    @Before
    fun createDb() {
        Dispatchers.setMain(testCoroutineDispatcher)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, WanAppDB::class.java).build()
        historyDao = db.historyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()

        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun writeUserAndReadInList() {
        val searchHistory = SearchHistory(Date(), "search_key")

        managedCoroutineScope.launch(Dispatchers.Default) {

            historyDao.insertSearchKey(searchHistory)
            assertThat(historyDao.getSearchHistory()[0]).isEqualTo(searchHistory)
        }
    }
}
