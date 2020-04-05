package com.tainzhi.android.wanandroid.ui.home

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.Banner
import com.tainzhi.android.wanandroid.databinding.ItemArticleBinding
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.util.GlideImageLoader
import com.tainzhi.android.wanandroid.util.Preference
import com.tainzhi.android.wanandroid.util.dp2px
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/24 下午5:42
 * @description:
 **/

class HomeFragment : BaseVMFragment<ArticleViewModel>() {
    private val isLogin by Preference(Preference.KEY_IS_LOGIN, false)
    private val homeArticleAdapter by lazy { HomeArticleAdapter<ItemArticleBinding>(R.layout.item_article, BR
        .article) }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private val banner by lazy { com.youth.banner.Banner(activity) }

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initVM(): ArticleViewModel = getViewModel()

    override fun initView() {

        initRecyclerView()
        initBanner()

        homeRefreshLayout.run {
            setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
            setOnRefreshListener { refresh() }
        }
    }

    override fun initData() {
        refresh()
    }

    private fun initRecyclerView() {
        homeRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
        }
        homeArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(homeArticleAdapter.data[position])
                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(homeArticleAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            setOnItemChildClickListener(this@HomeFragment.onItemChildClickListener)
            if (headerLayoutCount > 0) removeAllHeaderView()
            addHeaderView(banner)
            loadMoreModule.run {
                loadMoreView = CustomLoadMoreView()
                setOnLoadMoreListener { loadMore() }
            }
        }
        homeRecyclerView.adapter = homeArticleAdapter
    }

    private val onItemChildClickListener = OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.collectIv -> {
                if (isLogin) {
                    homeArticleAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyItemChanged(position + headerLayoutCount)
                    }
                } else {
                    Navigation.findNavController(homeRecyclerView).navigate(R.id
                            .action_tabHostFragment_to_login)
                }
            }
        }
    }

    private fun loadMore() {
        mViewModel.getHomeArticleList(false)
    }

    private fun initBanner() {

        banner.run {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner.dp2px(200))
            setBannerStyle(BannerConfig.TITLE_BACKGROUND)
            setImageLoader(GlideImageLoader())
            setDelayTime(3000)
            setOnBannerListener { position ->
                run {
                    val action = BrowserFragmentDirections.actionGlobalBrowserFragment(bannerUrls[position])
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun refresh() {
        homeArticleAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.getBanners()
        mViewModel.getHomeArticleList(true)
    }

    override fun startObserve() {
        mViewModel.apply {
            banners.observe(viewLifecycleOwner, Observer { it ->
                it?.let { setBanner(it) }
            })

            uiState.observe(viewLifecycleOwner, Observer {

                homeRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list ->
                    homeArticleAdapter.run {
                        if (it.isRefresh) setList(list.datas)
                        else addData(list.datas)
                        loadMoreModule.run {
                            isEnableLoadMore = true
                            loadMoreComplete()
                        }
                    }
                }

                if (it.showEnd) homeArticleAdapter.loadMoreModule.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "Net error" else message)
                }

            })
        }
    }

    private fun setBanner(bannerList: List<Banner>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        banner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
        banner.start()
    }

    override fun onResume() {
        super.onResume()
        homeRefreshLayout.isEnabled = true
        banner.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        homeRefreshLayout.isEnabled = false
        banner.stopAutoPlay()
    }
}