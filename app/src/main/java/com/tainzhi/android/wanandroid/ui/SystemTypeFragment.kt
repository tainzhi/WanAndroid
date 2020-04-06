package com.tainzhi.android.wanandroid.ui

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.util.Preference
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_system_type.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SystemTypeFragment : BaseVMFragment<ArticleViewModel>() {

    private val isLogin by Preference(Preference.KEY_IS_LOGIN, false)

    private val cid by lazy { arguments?.getInt(CID) }
    private val isBlog by lazy { arguments?.getBoolean(BLOG) ?: false } // 区分是体系下的文章列表还是公众号下的文章列表
    private val systemTypeAdapter by lazy { HomeArticleAdapter() }

    companion object {
        private const val CID = "cid"
        private const val BLOG = "blog"
        fun newInstance(cid: Int, isBlog: Boolean): SystemTypeFragment {
            val fragment = SystemTypeFragment()
            val bundle = Bundle()
            bundle.putBoolean(BLOG, isBlog)
            bundle.putInt(CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResId() = R.layout.fragment_system_type

    override fun initView() {
        initRecyclerView()
    }

    override fun initVM(): ArticleViewModel = getViewModel()

    override fun initData() {
        refresh()
    }

    private fun initRecyclerView() {
        systemTypeRefreshLayout.run {
            setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
            setOnRefreshListener { refresh() }
        }
        systemTypeAdapter.run {
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(systemTypeAdapter.data[position])

                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(systemTypeAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            setOnItemChildClickListener(this@SystemTypeFragment.onItemChildClickListener)

            loadMoreModule.run {
                loadMoreView = CustomLoadMoreView()
                setOnLoadMoreListener { loadMore() }
            }
        }
        systemTypeRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
            adapter = systemTypeAdapter
        }
    }

    private val onItemChildClickListener = OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.collectIv -> {
                if (isLogin) {
                    systemTypeAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(systemTypeRecyclerView).navigate(R.id
                            .action_tabHostFragment_to_login)
                }
            }
        }
    }

    private fun loadMore() {
        loadData(false)
    }

    private fun refresh() {
        systemTypeAdapter.loadMoreModule.isEnableLoadMore = false
        loadData(true)
    }


    private fun loadData(isRefresh: Boolean) {
        cid?.let {
            if (this.isBlog)
                mViewModel.getBlogArticleList(isRefresh, it)
            else
                mViewModel.getSystemTypeArticleList(isRefresh, it)
        }
    }

    override fun startObserve() {
        mViewModel.uiState.observe(viewLifecycleOwner, Observer {
            systemTypeRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                systemTypeAdapter.run {
                    if (it.isRefresh) setList(list.datas)
                    else addData(list.datas)
                    loadMoreModule.run {
                        isEnableLoadMore = true
                        loadMoreComplete()
                    }
                }
            }

            if (it.showEnd) systemTypeAdapter.loadMoreModule.loadMoreEnd()

            it.showError?.let { message ->
                activity?.toast(if (message.isBlank()) "Net error" else message)
            }
        })
    }
}