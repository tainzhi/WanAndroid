package com.tainzhi.android.wanandroid.ui.main

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.R.string
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentTabHostBinding
import com.tainzhi.android.wanandroid.ui.BlogFragment
import com.tainzhi.android.wanandroid.ui.MainFragment
import com.tainzhi.android.wanandroid.ui.project.ProjectFragment
import com.tainzhi.android.wanandroid.util.UpdateUtils
import com.tainzhi.android.wanandroid.util.toast
import kotlinx.android.synthetic.main.fragment_tab_host.*
import kotlinx.android.synthetic.main.main_drawer_nav_content_layout.*
import org.koin.android.ext.android.get

class TabHostFragment : BaseVMFragment<TabHostViewModel>(useBinding = true) {
    // 主页tab
    private val tabHostFragment by lazy { MainFragment() }
    
    // 博客tab
    private val blogFragment by lazy { BlogFragment() }
    
    // 项目分类tab
    private val projectFragment by lazy { ProjectFragment() }
    
    private val MAIN_INDEX = 0
    private val BLOG_INDEX = 1
    private val PROJECT_INDEX = 2
    
    private var exitTime = 0L
    
    private val fragmentList = listOf(tabHostFragment, blogFragment, projectFragment)
    
    override fun getLayoutResId() = R.layout.fragment_tab_host
    
    override fun initVM() = get<TabHostViewModel>()
    
    override fun initView() {
        
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressed)
        
        (mBinding as FragmentTabHostBinding).include.viewModel = mViewModel
        
        toolbar.title = getString(R.string.main)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.searchFragment -> {
                    findNavController().navigate(R.id.action_tabHostFragment_to_searchFragment)
                    true
                }
                else -> false
            }
        }
        toolbar.setNavigationOnClickListener { onBackPressed.isEnabled = true }
        
        initNavigationView()
        
        tabHostViewPager.run {
            adapter = object : FragmentStateAdapter(this@TabHostFragment) {
                
                override fun getItemCount() = fragmentList.size
                
                override fun createFragment(position: Int) = fragmentList[position]
            }
            // disable swipe left or right
            isUserInputEnabled = false
        }
        
        tabHostBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tabHostFragment -> {
                    tabHostViewPager.currentItem = MAIN_INDEX
                    toolbar.title = getString(R.string.main)
                }
                R.id.blogFragment -> {
                    tabHostViewPager.currentItem = BLOG_INDEX
                    toolbar.title = getString(R.string.blog)
                }
                R.id.projectFragment -> {
                    tabHostViewPager.currentItem = PROJECT_INDEX
                    toolbar.title = getString(R.string.project)
                }
            }
            true
        }
        
    }
    
    private fun initNavigationView() {
        val toggle = ActionBarDrawerToggle(
                activity, mainDrawerLayout, toolbar, string.navigation_drawer_open, string
                .navigation_drawer_close)
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    
        // mainDrawerLayoutNavigation.setupWithNavController(navController)
        userNameTv.setOnClickListener {
            closeDrawer()
            findNavController().navigate(R.id.action_tabHostFragment_to_login)
        }
        userImageIv.setOnClickListener {
            closeDrawer()
            findNavController().navigate(R.id.action_tabHostFragment_to_login)
        }
        myCollectionBtn.setOnClickListener {
            closeDrawer()
            findNavController().navigate(R.id.action_tabHostFragment_to_collectFragment)
        }
        browseHistoryBtn.setOnClickListener {
            closeDrawer()
            findNavController().navigate(R.id.action_tabHostFragment_to_historyFragment)
        }
        settingsBtn.setOnClickListener {
            closeDrawer()
            findNavController().navigate(R.id.action_tabHostFragment_to_settingsFragment)
        }
        logoutBtn.setOnClickListener {
            closeDrawer()
            activity?.toast(mViewModel.user.value?.nickname + getString(string.logout_success))
            mViewModel.logout()
        }
    }
    
    override fun initData() {
        // FIXME: 2020/3/11 初始化没有User的post，导致用户信息没有显示，原因还是用户登录状态的保持和传递
        // 尤其是没有使用Event bus，怎么处理，还需要深入
        mViewModel.getAppUpdateInfo()
        mViewModel.getUserInfo()
    }
    
    override fun startObserve() {
    
        mViewModel.updateInfo.observe(this, Observer { updateInfo ->
            UpdateUtils.newInstance().updateApp(activity as Context, updateInfo)
        })
    }
    
    private fun closeDrawer() {
        mainDrawerLayout.closeDrawer(GravityCompat.START)
    }
    
    private val onBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (mainDrawerLayout != null && mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                closeDrawer()
                // isEnabled = false
            } else if (System.currentTimeMillis() - exitTime > 2000) {
                exitTime = System.currentTimeMillis()
                activity?.toast(R.string.back_press_hint)
            } else {
                requireActivity().finish()
            }
        }
    }
    
}
