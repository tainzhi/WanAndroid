package com.tainzhi.android.wanandroid.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.Hot
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.util.Preference
import com.tainzhi.android.wanandroid.util.dp2px
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SearchFragment : BaseVMFragment<SearchViewModel>() {

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val searchAdapter by lazy { HomeArticleAdapter() }
    private var key = ""
    private val hotList = mutableListOf<Hot>()
    private val webSitesList = mutableListOf<Hot>()


    override fun getLayoutResId() = R.layout.fragment_search

    override fun initView() {
        initTagLayout()

        searchRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(searchRecyclerView.dp2px(10)))

        }
        initAdapter()
        searchRefreshLayout.setOnRefreshListener { refresh() }

        searchView.run {
            //            isIconified = false
//            onActionViewExpanded()
            setOnQueryTextListener(onQueryTextListener)
        }
    }

    override fun initVM(): SearchViewModel = getViewModel()

    private fun refresh() {
        searchAdapter.setEnableLoadMore(false)
        viewModel.searchHot(true, key)
    }

    private fun initAdapter() {
        searchAdapter.run {
            setOnItemClickListener { _, _, position ->
                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(searchAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            onItemChildClickListener = this@SearchFragment.onItemChildClickListener
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, homeRecyclerView)
        }
        searchRecyclerView.adapter = searchAdapter
        val emptyView = layoutInflater.inflate(R.layout.view_empty, searchRecyclerView.parent as ViewGroup, false)
        val emptyTv = emptyView.findViewById<TextView>(R.id.emptyTv)
        emptyTv.text = getString(R.string.try_another_key)
        searchAdapter.emptyView = emptyView
    }

    private fun loadMore() {
        viewModel.searchHot(false, key)
    }

    override fun initData() {
//        searchToolbar.setNavigationOnClickListener { onBackPressed() }
        viewModel.getHotSearch()
        viewModel.getWebSites()
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    searchAdapter.run {
                        data[position].run {
                            collect = !collect
                            viewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(searchRecyclerView).navigate(R.id
                            .action_mainFragment_to_loginFragment)
                }
            }
        }
    }


    private fun initTagLayout() {
        hotTagLayout.run {
            adapter = object : TagAdapter<Hot>(hotList) {
                override fun getCount() = hotList.size

                override fun getView(parent: FlowLayout, position: Int, t: Hot): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.tag,
                            parent, false) as TextView
                    tv.text = t.name
                    return tv
                }
            }

            setOnTagClickListener { _, position, _ ->
                key = hotList[position].name
                startSearch(key)
                true
            }
        }

        webTagLayout.run {
            adapter = object : TagAdapter<Hot>(webSitesList) {
                override fun getCount() = webSitesList.size

                override fun getView(parent: FlowLayout, position: Int, t: Hot): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.tag,
                            parent, false) as TextView
                    tv.text = t.name
                    return tv
                }
            }

            setOnTagClickListener { _, position, _ ->
                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(webSitesList[position].link)
                findNavController().navigate(action)
                true
            }
        }
    }

    private fun startSearch(key: String) {
        searchView.clearFocus()
        viewModel.searchHot(true, key)
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String?) = false

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                key = query
                startSearch(key)
            }
            return true
        }
    }

    override fun startObserve() {

        viewModel.uiState.observe(this, Observer {
            searchRecyclerView.visibility = if (it.showHot) View.GONE else View.VISIBLE
            hotContent.visibility = if (!it.showHot) View.GONE else View.VISIBLE
            searchRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                searchAdapter.run {
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) searchAdapter.loadMoreEnd()

            it.showHotSearch?.let { data ->
                hotList.clear()
                hotList.addAll(data)
                hotTagLayout.adapter.notifyDataChanged()
            }

            it.showWebSites?.let { data ->
                webSitesList.clear()
                webSitesList.addAll(data)
                webTagLayout.adapter.notifyDataChanged()
            }

        })

    }
}