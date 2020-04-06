package com.tainzhi.android.wanandroid.ui

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentSquareBinding
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_square.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SquareFragment : BaseVMFragment<ArticleViewModel>(useBinding = true) {

    private val squareAdapter by lazy { HomeArticleAdapter() }

    override fun getLayoutResId() = R.layout.fragment_square

    override fun initVM(): ArticleViewModel = getViewModel()

    override fun initView() {
        (mBinding as FragmentSquareBinding).viewModel = mViewModel
        initRecyclerView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecyclerView() {
        squareAdapter.run {
            showCollect(false)
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(squareAdapter.data[position])

                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(squareAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            loadMoreModule.run {
                loadMoreView = CustomLoadMoreView()
                setOnLoadMoreListener { loadMore() }
            }
        }
        squareRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
            adapter = squareAdapter
        }
    }

    private fun loadMore() {
        mViewModel.getSquareArticleList(false)
    }

    private fun refresh() {
        squareAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.getSquareArticleList(true)
    }

    override fun startObserve() {
        mViewModel.uiState.observe(viewLifecycleOwner, Observer {

            it.showSuccess?.let { list ->
                squareAdapter.run {
                    if (it.isRefresh) setList(list.datas)
                    else addData(list.datas)
                    loadMoreModule.run {
                        isEnableLoadMore = true
                        loadMoreComplete()
                    }
                }
            }
    
            if (it.showEnd) squareAdapter.loadMoreModule.loadMoreEnd()
    
            it.showError?.let { message ->
                activity?.toast(if (message.isBlank()) "Net error" else message)
            }
    
        })
    }
    
    override fun onPause() {
        super.onPause()
        squareRefreshLayout.isEnabled = false
    }
    
    override fun onResume() {
        super.onResume()
        squareRefreshLayout.isEnabled = true
    }
    
}