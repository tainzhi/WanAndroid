package com.tainzhi.android.wanandroid.utils

import android.app.Application
import android.content.Context
import com.tainzhi.android.wanandroid.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.testAppModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mock

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/2 下午4:54
 * @description:
 **/
@ExperimentalCoroutinesApi
class MainCoroutineScopeRule constructor(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
        TestWatcher(),
        TestCoroutineScope by TestCoroutineScope(dispatcher) {

    @Mock
    private lateinit var mockContext: Context

    override fun starting(description: Description?) {
        super.starting(description)
        // If your codebase allows the injection of other dispatchers like
        // Dispatchers.Default and Dispatchers.IO, consider injecting all of them here
        // and renaming this class to `CoroutineScopeRule`
        //
        // All injected dispatchers in a test should point to a single instance of
        // TestCoroutineDispatcher.
        Dispatchers.setMain(dispatcher)

        mockContext = Application()

        // 替换掉默认的 Dispatchers.default, 它不能用于测试
        val testCoroutineModule = module {
            single { CoroutinesDispatcherProvider() }
        }
        startKoin {
            androidContext(mockContext)
            modules(testAppModule + testCoroutineModule)
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()

        stopKoin()
    }
}
