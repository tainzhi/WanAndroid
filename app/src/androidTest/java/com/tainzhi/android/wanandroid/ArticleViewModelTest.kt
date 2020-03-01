package com.tainzhi.android.wanandroid

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import com.tainzhi.android.wanandroid.utils.getValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
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
class ArticleViewModelTest : KoinTest {

    private lateinit var viewModel: ArticleViewModel

    @Before
    fun `setup`() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        WanApp.kt已经startKoin
//        startKoin {
//            androidContext(appContext)
//            modules(appModule)
//        }
        viewModel = get()
    }

    @Test
    fun homeArticle() {
        GlobalScope.launch {
            viewModel.getHomeArticleList()
            val uiState = getValue(viewModel.uiState)
            assertThat(uiState.showSuccess?.size, not(0))
        }
    }

    @After
    fun `stop`() {
//        stopKoin()
    }
}