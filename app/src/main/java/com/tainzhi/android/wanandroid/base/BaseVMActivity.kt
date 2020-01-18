package com.tainzhi.android.wanandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 15:26
 * @description:
 **/

abstract class BaseVMActivity<VM : BaseViewModel>(useBinding: Boolean = false): AppCompatActivity
() {
    private val _useBinding = useBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
        if (!_useBinding) setContentView(getLayoutResId())
        initView()
        initData()
    }

    open fun getLayoutResId(): Int = 0
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}