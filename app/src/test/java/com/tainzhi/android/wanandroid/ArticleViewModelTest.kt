package com.tainzhi.android.wanandroid

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import com.tainzhi.android.wanandroid.utils.MainCoroutineScopeRule
import com.tainzhi.android.wanandroid.utils.captureValues
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 下午9:16
 * @description:
 **/

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ArticleViewModelTest : KoinTest {

    private lateinit var viewModel: ArticleViewModel

    @Mock
    private lateinit var mockContext: Context

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScope = MainCoroutineScopeRule()

    @Before
    fun `setup`() {
        mockContext = Application()
        startKoin {
            androidContext(mockContext)
            modules(testAppModule)
        }

        viewModel = get()
    }

    @Test
    fun homeArticle() {
        mainCoroutineScope.dispatcher.runBlockingTest {
            viewModel.getHomeArticleList()
            viewModel.uiState.captureValues {
                assertThat(values.size, equalTo(1))
            }
        }
    }

    @After
    fun `tearDown`() {
        stopKoin()
    }
}