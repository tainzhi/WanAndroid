package com.tainzhi.android.wanandroid.ui.history

import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HistoryFragment : BaseVMFragment<HistoryViewModel>() {

    override fun getLayoutResId() = R.layout.fragment_history

    override fun initVM(): HistoryViewModel = getViewModel()

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startObserve() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}