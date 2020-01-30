package com.tainzhi.android.wanandroid.ui.home

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.Banner
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import com.tainzhi.android.wanandroid.ui.BrowserActivity
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
    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val homeArticleAdapter by lazy { HomeArticleAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private val banner by lazy { com.youth.banner.Banner(activity) }

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initVM(): ArticleViewModel = getViewModel()

    override fun initView() {

        initRecycleView()
        initBanner()

        homeRefreshLayout.run {
            setOnRefreshListener { refresh() }
        }
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {
        homeRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10)))
        }
        homeArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = bundleOf(BrowserActivity.URL to homeArticleAdapter.data[position].link)
                Navigation.findNavController(homeRecycleView).navigate(R.id
                        .action_tabFragment_to_browserActivity, bundle)
            }
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            if (headerLayoutCount > 0) removeAllHeaderView()
            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, homeRecycleView)
        }
        homeRecycleView.adapter = homeArticleAdapter
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    homeArticleAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(homeRecycleView).navigate(R.id.action_tab_to_login)
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
                    Navigation.findNavController(banner).navigate(R.id.action_tabFragment_to_browserActivity, bundleOf
                    (BrowserActivity.URL to bannerUrls[position]))
                }
            }
        }
    }

    fun refresh() {
        homeArticleAdapter.setEnableLoadMore(false)
        mViewModel.getHomeArticleList(true)
    }

    override fun startObserve() {
        mViewModel.apply {
            mBanners.observe(this@HomeFragment, Observer { it ->
                it?.let { setBanner(it) }
            })

            uiState.observe(this@HomeFragment, Observer {

                homeRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list ->
                    homeArticleAdapter.run {
                        if (it.isRefresh) replaceData(list.datas)
                        else addData(list.datas)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if (it.showEnd) homeArticleAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
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

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }
}