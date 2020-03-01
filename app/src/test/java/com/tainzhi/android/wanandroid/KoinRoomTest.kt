package com.tainzhi.android.wanandroid

import com.tainzhi.android.wanandroid.bean.SearchHistory
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.db.WanAppDB
import com.tainzhi.android.wanandroid.di.appModule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.get
import java.io.IOException
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 下午4:43
 * @description:
 **/

class KoinRoomTest : KoinTest {
    private lateinit var historyDao: HistoryDao
    private lateinit var db: WanAppDB

    @Before
    fun createDb() {
        startKoin { appModule }
        historyDao = get()
        db = get()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAndReadInList() {
        val searchHistory = SearchHistory(Date(), "search_key")

        GlobalScope.launch {

            historyDao.insertSearchKey(searchHistory)
            assertEquals(historyDao.getSearchHistory()[0], searchHistory)
        }
    }
}