package com.tainzhi.android.wanandroid.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 14:57
 * @description:
 **/

abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}