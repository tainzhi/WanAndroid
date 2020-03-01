package com.tainzhi.android.wanandroid

import com.tainzhi.android.wanandroid.di.appModule
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.get

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 下午4:31
 * @description:
 **/

class MyKoinTest : KoinTest {
    var articleViewModel: ArticleViewModel? = null

    @Test
    fun `should inject my components`() {
//        WanApp.kt已经startKoin
//        startKoin {
//            androidContext(appContext)
//            modules(appModule)
//        }
        articleViewModel = get()
        assertNotNull(articleViewModel)
    }

    @Test
    fun `check modules`() {
        checkModules {
            appModule
        }
    }

    @After
    fun `stop`() {
//        stopKoin()
    }
}