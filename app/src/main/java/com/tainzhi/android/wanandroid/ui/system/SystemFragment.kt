package com.tainzhi.android.wanandroid.ui.system

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.BaseBindAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.SystemParent
import com.tainzhi.android.wanandroid.ui.SystemTypeNormalActivity
import com.tainzhi.android.wanandroid.util.dp2px
import com.tainzhi.android.wanandroid.util.startKtxActivity
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_system.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


/**
 * 体系
 */
class SystemFragment : BaseVMFragment<SystemViewModel>() {
    private val systemAdapter by lazy { BaseBindAdapter<SystemParent>(R.layout.item_system, BR.systemParent) }

    override fun getLayoutResId() = R.layout.fragment_system

    override fun initVM(): SystemViewModel = getViewModel()

    override fun initView() {
        initRecycleView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {

        systemRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(systemRecycleView.dp2px(10)))
            adapter = systemAdapter
        }

        systemAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            startKtxActivity<SystemTypeNormalActivity>(value = SystemTypeNormalActivity.ARTICLE_LIST to systemAdapter.data[position])
        }

        systemRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        mViewModel.getSystemTypes()
    }

    override fun startObserve() {
        mViewModel.run {
            uiState.observe(this@SystemFragment, Observer {
                systemRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list -> systemAdapter.replaceData(list) }

                it.showError?.let { message -> activity?.toast(message) }
            })
        }
    }
}