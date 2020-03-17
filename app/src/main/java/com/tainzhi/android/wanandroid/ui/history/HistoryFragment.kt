package com.tainzhi.android.wanandroid.ui.history

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.BaseBindAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.databinding.FragmentHistoryBinding
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HistoryFragment : BaseVMFragment<HistoryViewModel>(useBinding = true) {

    private val historyAdapter by lazy {
        BaseBindAdapter<BrowseHistory>(R.layout.item_search_history, BR.browseHistory)
    }

    override fun getLayoutResId() = R.layout.fragment_history

    override fun initVM(): HistoryViewModel = getViewModel()

    override fun initView() {
    
        initToolbar()
    
        requireActivity().onBackPressedDispatcher.addCallback { onBack() }
    
        historySwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
    
        (mBinding as FragmentHistoryBinding).viewModel = mViewModel
    
        historyRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
        }
    
        initAdapter()
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.browse_history)
        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        toolbar.inflateMenu(R.menu.delete_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteAll -> mViewModel.deleteBrowseHistory()
                else -> Unit
            
            }
            false
        }
    }

    override fun initData() {
        refresh()
    }

    private fun initAdapter() {
        historyAdapter.run {
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(historyAdapter.data[position].article)

                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(historyAdapter
                        .data[position].article.link)
                findNavController().navigate(action)
            }
            setEnableLoadMore(false)
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

    private fun refresh() {
        historyAdapter.setEnableLoadMore(false)
        mViewModel.getBrowseHistory(isRefresh = true)
    }

    private fun loadMore() {
        mViewModel.getBrowseHistory(isRefresh = false)
    }

    override fun startObserve() {
        mViewModel.apply {
            uiState.observe(viewLifecycleOwner, Observer {
                it.showSuccesses?.let { list ->
                    historyAdapter.run {
                        if (it.isRefresh) replaceData(list)
                        else addData(list)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }
                if (it.isDelete) {
                    historyAdapter.setNewData(null)
                }
                if (it.showEnd) {
                    historyAdapter.loadMoreEnd()
                }
            })
        }
    }
    
    private fun onBack() {
        findNavController().navigateUp()
    }
}