package com.tainzhi.android.wanandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tainzhi.android.wanandroid.bean.SearchHistory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 下午4:43
 * @description:
 **/

@RunWith(AndroidJUnit4::class)
class KoinRoomTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        db = Room.inMemoryDatabaseBuilder(
//                context, WanAppDB::class.java).build()
    }

    @Before fun `setup`() {
//        WanApp.kt已经startKoin
//        startKoin {
//            androidContext(appContext)
//            modules(appModule)
//        }
    }

    @After fun `reset`() {
//        stopKoin()
    }


    @Test
    fun writeUserAndReadInList() {
        val searchHistory = SearchHistory(Date(), "search_key")

        GlobalScope.launch {

//            historyDao.insertSearchKey(searchHistory)
////            assertEquals(historyDao.getSearchHistory()[0], searchHistory)
//            assertThat(historyDao.getSearchHistory()[0], equalTo(searchHistory))
        }
    }
}