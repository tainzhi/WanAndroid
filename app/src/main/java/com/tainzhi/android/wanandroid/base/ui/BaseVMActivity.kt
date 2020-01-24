package com.tainzhi.android.wanandroid.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 15:26
 * @description:
 **/

abstract class BaseVMActivity<VM : BaseViewModel>(useBinding: Boolean = false): AppCompatActivity
() {
    private val _useBinding = useBinding
    protected lateinit var binding: ViewDataBinding
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
        if (!_useBinding) setContentView(getLayoutResId())
        initView()
        initData()
    }

    open fun getLayoutResId(): Int = 0
    abstract fun initVM(): VM
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}