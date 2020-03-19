package com.tainzhi.android.wanandroid.ui

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentCollectBinding
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_collect.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CollectFragment : BaseVMFragment<ArticleViewModel>(useBinding = true) {
    private val articleAdapter by lazy { HomeArticleAdapter() }
    override fun getLayoutResId() = R.layout.fragment_collect

    override fun initVM(): ArticleViewModel = getViewModel()

    override fun initView() {
        toolbar.setTitle(R.string.my_collection)
        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        requireActivity().onBackPressedDispatcher.addCallback { onBack() }
    
        collectRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
    
        (mBinding as FragmentCollectBinding).viewModel = mViewModel
    
        collectRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
        }

        initAdapter()
    }

    override fun initData() {
        refresh()
    }

    private fun initAdapter() {
        articleAdapter.run {
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(articleAdapter.data[position])

                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(articleAdapter
                                                                                           .data[position].link)
                findNavController().navigate(action)

            }
            onItemChildClickListener = itemChildClickListener
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, collectRecyclerView)
        }
        collectRecyclerView.adapter = articleAdapter

        val emptyView = layoutInflater.inflate(R.layout.view_empty, collectRecyclerView.parent as
                ViewGroup, false)
        val emptyTv = emptyView.findViewById<TextView>(R.id.emptyTv)
        emptyTv.text = getString(R.string.no_collection)
        articleAdapter.emptyView = emptyView
    }

    private val itemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.collectIv -> {
                articleAdapter.run {
                    data[position].run {
                        collect = !collect
                        mViewModel.collectArticle(originId, collect)
                    }
                    data.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    private fun refresh() {
        articleAdapter.setEnableLoadMore(false)
        mViewModel.getCollectArticleList(true)
    }

    private fun loadMore() {
        mViewModel.getCollectArticleList(false)
    }

    override fun startObserve() {

        mViewModel.apply {

            uiState.observe(viewLifecycleOwner, Observer {

                it.showSuccess?.let { list ->
                    list.datas.forEach { it.collect = true }
                    articleAdapter.run {
                        if (it.isRefresh) replaceData(list.datas)
                        else addData(list.datas)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }
    
                if (it.showEnd) articleAdapter.loadMoreEnd()
    
                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "Net error" else message)
                }
            })
        }
    }
    
    private fun onBack() {
        findNavController().navigateUp()
    }
    
}