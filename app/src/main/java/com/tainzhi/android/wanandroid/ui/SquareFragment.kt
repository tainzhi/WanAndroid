package com.tainzhi.android.wanandroid.ui

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.BaseBindAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.databinding.FragmentSquareBinding
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_square.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SquareFragment : BaseVMFragment<ArticleViewModel>(useBinding = true) {

    private val squareAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_square, BR.article) }

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
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(squareAdapter.data[position])

                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(squareAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, squareRecyclerView)
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
        squareAdapter.setEnableLoadMore(false)
        mViewModel.getSquareArticleList(true)
    }

    override fun startObserve() {
        mViewModel.uiState.observe(viewLifecycleOwner, Observer {

            it.showSuccess?.let { list ->
                squareAdapter.run {
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) squareAdapter.loadMoreEnd()

            it.needLogin?.let { needLogin ->
                // TODO: 2020/1/28 登录相关的 
                //                if (needLogin) Navigation.findNavController(squareRecyclerView).navigate(R.id.action_tab_to_login)
//                if (needLogin) Navigation.findNavController(squareRecyclerView).navigate(R.id.action_tab_to_login)
//                else Navigation.findNavController(squareRecyclerView).navigate(R.id.action_tab_to_share)
            }

            it.showError?.let { message ->
                activity?.toast(if (message.isBlank()) "网络异常" else message)
            }

        })
    }

}