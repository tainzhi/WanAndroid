package com.tainzhi.android.wanandroid

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.internal.wait
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 下午9:16
 * @description:
 **/

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ArticleViewModelInstrumentedTest : KoinTest {

    @Test
    fun homeArticle() = runBlockingTest{
        val viewModel = get<ArticleViewModel>()
        viewModel.getHomeArticleList()
        val result = viewModel.uiState.value
//        assertThat(result., equalTo(1))
    }
}