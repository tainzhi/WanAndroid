package com.tainzhi.android.wanandroid.ui.history

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentHistoryBinding
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.util.dp2px
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_system_type_normal.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HistoryFragment : BaseVMFragment<HistoryViewModel>(useBinding = true) {

    private val historyAdapter by lazy { HomeArticleAdapter() }

    override fun getLayoutResId() = R.layout.fragment_history

    override fun initVM(): HistoryViewModel = getViewModel()

    override fun initView() {
        toolbar.setTitle(R.string.browse_history)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        (mBinding as FragmentHistoryBinding).viewModel = viewModel
        historyRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(historyRecyclerView.dp2px(10)))
        }

        initAdapter()
    }

    override fun initData() {
        viewModel.getBrowseHistory()
    }

    private fun initAdapter() {
        historyAdapter.run {
            setOnItemClickListener { _, _, position ->
                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(historyAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
//            setLoadMoreView(CustomLoadMoreView())
//            setOnLoadMoreListener({ loadMore() }, collectRecyclerView)
        }
        historyRecyclerView.adapter = historyAdapter
    }

    override fun startObserve() {
        viewModel.apply {
            uiState.observe(this@HistoryFragment, Observer {
                it.showSuccess?.let { list ->
                    historyAdapter.addData(list.map { it.article })
                }
            })
        }
    }
}