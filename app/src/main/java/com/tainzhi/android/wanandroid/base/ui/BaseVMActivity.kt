package com.tainzhi.android.wanandroid.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 15:26
 * @description:
 **/

abstract class BaseVMActivity<VM : BaseViewModel>(useBinding: Boolean = false): AppCompatActivity
() {
    private val _useBinding = useBinding
    protected lateinit var mBinding: ViewDataBinding
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initVM()
        startObserve()

        if (_useBinding) {
            mBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, getLayoutResId())
            mBinding.lifecycleOwner = this
        } else {
            setContentView(getLayoutResId())
        }
        initView()
        initData()
    }

    open fun getLayoutResId(): Int = 0
    abstract fun initVM(): VM
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}