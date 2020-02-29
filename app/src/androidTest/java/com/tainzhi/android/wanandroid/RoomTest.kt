package com.tainzhi.android.wanandroid

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.db.WanAppDB
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 上午12:13
 * @description:
 **/

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var historyDao: HistoryDao
    private lateinit var db: WanAppDB


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, WanAppDB::class.java).build()
        historyDao = db.historyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val searchKey = "search_key"
        historyDao.insertSearchKey(searchKey)

        // TODO: 2020/3/1 coroutine
//        coroutineScope {
//
//            assertThat(historyDao.getSearchHistory()[0].searchKey, equalTo(searchKey))
//        }
    }
}
