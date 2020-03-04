package com.tainzhi.android.wanandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import com.tainzhi.android.wanandroid.utils.MainCoroutineScopeRule
import com.tainzhi.android.wanandroid.utils.captureValues
import com.tainzhi.android.wanandroid.utils.getOrAwaitValue
import com.tainzhi.android.wanandroid.utils.observeForTesting
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get
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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScope = MainCoroutineScopeRule()

    @Test
    fun homeArticle() {
        viewModel = get()
        viewModel.getHomeArticleList()
        mainCoroutineScope.runBlockingTest {
            var uiState = viewModel.uiState.getOrAwaitValue()
            assertThat(uiState.showLoading, `is`(true))
            uiState = viewModel.uiState.getOrAwaitValue()
            assertThat(uiState.showLoading, `is`(false))
//            var count = 0
//            viewModel.uiState.captureValues {
//                assertThat(values.size, equalTo(count))
//                when (count) {
//                    1 -> assertThat(values[count-1]?.showLoading, `is`(true))
//                    2 -> assertThat(values[count-1]?.showSuccess, `is`(nullValue()))
//                    3 -> assertThat(values[count-1]?.showSuccess, `is`(notNullValue()))
//                }
//                assertThat(values.size, equalTo(1))
//                count++
//            }
        }
    }
}