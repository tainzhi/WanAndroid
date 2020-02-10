package com.tainzhi.android.wanandroid.ui

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentCollectBinding
import com.tainzhi.android.wanandroid.util.dp2px
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.fragment_system_type_normal.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CollectFragment : BaseVMFragment<ArticleViewModel>(useBinding = true) {
    private val articleAdapter by lazy { HomeArticleAdapter() }
    override fun getLayoutResId() = R.layout.fragment_collect

    override fun initVM(): ArticleViewModel = getViewModel()

    override fun initView() {
        (mBinding as FragmentCollectBinding).viewModel = viewModel
        toolbar.setTitle(R.string.my_collect)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener { onDestroy() }

        collectRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(collectRecycleView.dp2px(10)))
        }

        initAdapter()
    }

    override fun initData() {
        refresh()
    }

    private fun initAdapter() {
        articleAdapter.run {
            //            showStar(false)
            setOnItemClickListener { _, _, position ->
                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(articleAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            onItemChildClickListener = itemChildClickListener
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, collectRecycleView)
        }
        collectRecycleView.adapter = articleAdapter
    }

    private fun refresh() {
        viewModel.getCollectArticleList(true)
    }


    private val itemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                articleAdapter.run {
                    data[position].run {
                        collect = !collect
                        viewModel.collectArticle(originId, collect)
                    }
                    notifyItemRemoved(position)
                }
            }
        }
    }

    private fun loadMore() {
        viewModel.getCollectArticleList(false)
    }

    override fun startObserve() {

        viewModel.apply {

            uiState.observe(this@CollectFragment, Observer {

                it.showSuccess?.let { list ->
                    articleAdapter.setEnableLoadMore(false)
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
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
                }
            })
        }
    }

}