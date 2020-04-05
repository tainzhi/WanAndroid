package com.tainzhi.android.wanandroid.ui.navigation

import android.view.MotionEvent
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.NavigationAdapter
import com.tainzhi.android.wanandroid.adapter.VerticalTabAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.Navigation
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_navigation.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

class NavigationFragment : BaseVMFragment<NavigationViewModel>() {
    
    private var touchLeft = true // 0 left, 1 right
    private val navigationList = mutableListOf<Navigation>()
    private val tabAdapter by lazy { VerticalTabAdapter(navigationList.map { it.name }) }
    private val navigationAdapter by lazy {
        NavigationAdapter(click = { article ->
            mViewModel.insertBrowseHistory(article)

            val action = BrowserFragmentDirections.actionGlobalBrowserFragment(article.link)
            findNavController().navigate(action)
        })
    }
    override fun getLayoutResId() = R.layout.fragment_navigation

    override fun initVM(): NavigationViewModel = getViewModel()

    override fun initView() {
        navigationRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
            adapter = navigationAdapter
            addOnScrollListener(scrollListener)
            // 右滑动， 而不是左滑动
            addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                    touchLeft = false
                }
        
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    touchLeft = false
                    return false
                }
        
                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                    touchLeft = false
                }
            })
        }

        initTabLayout()
    }

    private fun initTabLayout() {
        tabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {
            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                touchLeft = true
                scrollToPosition(position)
            }
        })
    }

    private fun scrollToPosition(position: Int) {
        val linearLayoutManager = navigationRecyclerView.layoutManager as
                LinearLayoutManager
        val firstPotion = linearLayoutManager.findFirstVisibleItemPosition()
    
        if (position > firstPotion) {
            //向后
            val scroller: LinearSmoothScroller = object : LinearSmoothScroller(activity) {
                override fun getHorizontalSnapPreference(): Int {
                    return SNAP_TO_START //具体见源码注释
                }
            
                override fun getVerticalSnapPreference(): Int {
                    return SNAP_TO_START //具体见源码注释
                }
            }
            scroller.targetPosition = position
            linearLayoutManager.startSmoothScroll(scroller)
        } else {
            navigationRecyclerView.smoothScrollToPosition(position)
        }
    }

    override fun initData() {
        mViewModel.getNavigation()
    }

    override fun startObserve() {
        mViewModel.run {
            navigationList.observe(viewLifecycleOwner, Observer {
                it?.run { getNavigation(it) }
            })
        }
    }
    
    private fun getNavigation(navigationList: List<Navigation>) {
        this.navigationList.clear()
        this.navigationList.addAll(navigationList)
        tabLayout.setTabAdapter(tabAdapter)
        
        navigationAdapter.setNewInstance(navigationList.toMutableList())
    }
    
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            // 手动滑动右边，待idle后，左边onTabSelected
            if (newState == SCROLL_STATE_IDLE && !touchLeft) {
                val linearLayoutManager = navigationRecyclerView.layoutManager as
                        LinearLayoutManager
                val firstPotion = linearLayoutManager.findFirstVisibleItemPosition()
                tabLayout.setTabSelected(firstPotion)
            }
        }
    }
}