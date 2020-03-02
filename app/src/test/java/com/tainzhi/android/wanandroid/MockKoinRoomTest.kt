package com.tainzhi.android.wanandroid

import android.app.Application
import android.content.Context
import com.tainzhi.android.wanandroid.bean.SearchHistory
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.di.appModule
import com.tainzhi.android.wanandroid.utils.MainCoroutineScopeRule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 下午4:43
 * @description:
 **/

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MockKoinRoomTest : KoinTest {

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

//    @Mock
//    private lateinit var mockContext: Context

    @Before
    fun `setup`() {
//        mockContext = Application()
        val mockContext = mock(Application::class.java)
        startKoin {
            androidContext(mockContext)
            modules(appModule)
        }
    }

    @Test
    fun writeUserAndReadInList() {

        mainCoroutineScopeRule.launch {
            val searchHistory = SearchHistory(Date(), "search_key")
            val historyDao: HistoryDao = get()

            historyDao.insertSearchKey(searchHistory)

            val queryResult = historyDao.getSearchHistory()
            assertThat(queryResult[0], equalTo(searchHistory))
        }
    }

    @After
    fun `reset`() {
        stopKoin()
    }
}