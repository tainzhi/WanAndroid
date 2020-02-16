package com.tainzhi.android.wanandroid.ui.history

import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.BaseBindAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.databinding.FragmentHistoryBinding
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.util.dp2px
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_system_type_normal.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HistoryFragment : BaseVMFragment<HistoryViewModel>(useBinding = true) {

    private val historyAdapter by lazy {
        BaseBindAdapter<BrowseHistory>(R.layout.item_search_history, BR.browseHistory)
    }

    override fun getLayoutResId() = R.layout.fragment_history

    override fun initVM(): HistoryViewModel = getViewModel()

    override fun initView() {

        initToolbar()

        (mBinding as FragmentHistoryBinding).viewModel = viewModel

        historyRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(historyRecyclerView.dp2px(10)))
        }

        initAdapter()
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.browse_history)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id
                    .mainFragment, false)
        }
        toolbar.inflateMenu(R.menu.delete_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteAll -> viewModel.deleteBrowseHistory()
                else -> Unit

            }
            false
        }
    }

    override fun initData() {
        viewModel.getBrowseHistory(isRefresh = false)
    }

    private fun initAdapter() {
        historyAdapter.run {
            setOnItemClickListener { _, _, position ->
                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(historyAdapter
                        .data[position].article.link)
                findNavController().navigate(action)
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, historyRecyclerView)
        }
        historyRecyclerView.adapter = historyAdapter

        val emptyView = layoutInflater.inflate(R.layout.view_empty, historyRecyclerView.parent as
                ViewGroup, false)
        val emptyTv = emptyView.findViewById<TextView>(R.id.emptyTv)
        emptyTv.text = getString(R.string.no_history)
        historyAdapter.emptyView = emptyView
    }

    private fun loadMore() {

    }

    override fun startObserve() {
        viewModel.apply {
            uiState.observe(this@HistoryFragment, Observer {
                it.showSuccesses?.let { list ->
                    //                    historySwipeRefreshLayout.isRefreshing = it.showLoading
//                    historyAdapter.setNewData(list)

                    historyAdapter.run {
                        setEnableLoadMore(false)
                        if (it.isRefresh) replaceData(list)
                        else addData(list)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }
                if (it.isDelete) {
//                    historySwipeRefreshLayout.isRefreshing = false
                    historyAdapter.setNewData(null)
                }
            })
        }
    }
}