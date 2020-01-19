package com.tainzhi.android.wanandroid.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 15:28
 * @description:
 **/

abstract class BaseVMFragment<VM: BaseViewModel>(useBinding: Boolean = false): Fragment() {

    private val _useBinding = useBinding
    protected lateinit var mBinding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (_useBinding) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
            mBinding.root
        } else
            inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}