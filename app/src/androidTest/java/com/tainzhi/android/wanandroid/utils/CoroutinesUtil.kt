package com.tainzhi.android.wanandroid.utils

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 下午6:07
 * @description:
 **/

interface ManagedCoroutineScope : CoroutineScope {
    abstract fun launch(bloack: suspend CoroutineScope.() -> Unit): Job
}

class LifecycleManagedCoroutineScope(val lifecycleCoroutineScope: LifecycleCoroutineScope,
                                     override val coroutineContext: CoroutineContext) : ManagedCoroutineScope {
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job = lifecycleCoroutineScope.launchWhenStarted(block)
}

class TestScope(override val coroutineContext: CoroutineContext) : ManagedCoroutineScope {
    val scope = TestCoroutineScope(coroutineContext)
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch {
            block.invoke(this)
        }
    }
}