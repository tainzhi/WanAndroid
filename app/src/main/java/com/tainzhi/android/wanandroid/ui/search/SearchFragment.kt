package com.tainzhi.android.wanandroid.ui.search

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.kennyc.view.MultiStateView
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.Hot
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.util.Preference
import com.tainzhi.android.wanandroid.util.exitCircularReveal
import com.tainzhi.android.wanandroid.util.startCircularReveal
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.math.hypot

class SearchFragment : BaseVMFragment<SearchViewModel>() {

    private val isLogin by Preference(Preference.KEY_IS_LOGIN, false)
    private val searchAdapter by lazy { HomeArticleAdapter() }
    private var key = ""
    private val searchHistoryList = mutableListOf<String>()
    private val hotList = mutableListOf<Hot>()
    private val webSitesList = mutableListOf<Hot>()


    override fun getLayoutResId() = R.layout.fragment_search

    override fun initView() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed)

        searchRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))

        }
        initAdapter()
        initTagLayout()
        searchRefreshLayout.run {
            setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
            setOnRefreshListener { refresh() }
        }

        searchView.run {
            // isIconified = false
            // onActionViewExpanded()
            setOnQueryTextListener(onQueryTextListener)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 不能在这里设定CircularReveal的中心, 此时view.right, view.bottom全部为0
        view.startCircularReveal()
    }

    override fun initVM(): SearchViewModel = getViewModel()

    private fun refresh() {
        searchAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.searchHot(true, key)
    }

    private fun initAdapter() {
        searchAdapter.run {
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(searchAdapter.data[position])

                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(searchAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            setOnItemChildClickListener(this@SearchFragment.onItemChildClickListener)
            loadMoreModule.run {
                loadMoreView = CustomLoadMoreView()
                setOnLoadMoreListener { loadMore() }
            }
        }
        searchRecyclerView.adapter = searchAdapter
    }

    private fun loadMore() {
        mViewModel.searchHot(false, key)
    }

    override fun initData() {
        mViewModel.getSearchHistory()
        mViewModel.getHotSearch()
        mViewModel.getWebSites()
    }

    private val onItemChildClickListener = OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.collectIv -> {
                if (isLogin) {
                    searchAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyItemChanged(position + headerLayoutCount)
                    }
                } else {
                    Navigation.findNavController(searchRecyclerView).navigate(R.id
                            .action_tabHostFragment_to_login)
                }
            }
        }
    }


    private fun initTagLayout() {

        searchHistoryTagLayout.run {
            adapter = object : TagAdapter<String>(searchHistoryList) {
                override fun getCount() = searchHistoryList.size

                override fun getView(parent: FlowLayout, position: Int, t: String?): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.tag,
                            parent, false) as TextView
                    tv.text = t
                    return tv
                }
            }

            setOnTagClickListener { _, position, _ ->
                key = searchHistoryList[position]
                startSearch(key)
                true
            }

        }

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
        mViewModel.insertSearchHistory(key)
        mViewModel.searchHot(true, key)
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

        mViewModel.uiState.observe(viewLifecycleOwner, Observer {
            searchRecyclerView.visibility = if (it.showHot) View.GONE else View.VISIBLE
            hotContent.visibility = if (!it.showHot) View.GONE else View.VISIBLE
            searchRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                searchAdapter.run {
                    if (it.isRefresh) setList(list.datas)
                    else addData(list.datas)
                    loadMoreModule.run {
                        isEnableLoadMore = true
                        loadMoreComplete()
                    }
                }
                searchMultiStateView.viewState = MultiStateView.ViewState.CONTENT
            }

            if (it.showEnd) {
                if (searchAdapter.data.isEmpty() && !it.showHot) {
                    searchMultiStateView.viewState = MultiStateView.ViewState.EMPTY
                }
                searchAdapter.loadMoreModule.loadMoreEnd()
            }

            it.showSearchHistory?.let { data ->
                searchHistoryList.clear()
                searchHistoryList.addAll(data)
                searchHistoryTagLayout.adapter.notifyDataChanged()
            }

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

    override fun onResume() {
        super.onResume()
        searchRefreshLayout.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        searchRefreshLayout.isEnabled = false
    }

    private val onBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 如果是在搜索结果页面，那么返回，将隐藏搜索结果页面，显示热搜页面
            if (searchAdapter.data.size != 0 ||
                    searchMultiStateView.viewState != MultiStateView.ViewState.CONTENT) {
                searchMultiStateView.viewState = MultiStateView.ViewState.CONTENT
                searchAdapter.setList(null)
                searchRecyclerView.visibility = View.INVISIBLE
                hotContent.visibility = View.VISIBLE
                // 更新搜索记录
                mViewModel.getSearchHistory()
            } else {
                view?.exitCircularReveal(view!!.right, view!!.top) {
                    // popBackStack()没有放置在这里, 是因为可能出现Search页面会重现
                }
                findNavController().popBackStack()
            }
        }
    }
}
