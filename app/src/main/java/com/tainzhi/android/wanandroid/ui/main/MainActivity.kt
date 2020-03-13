package com.tainzhi.android.wanandroid.ui.main

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.base.ui.BaseVMActivity
import com.tainzhi.android.wanandroid.databinding.ActivityMainBinding
import com.tainzhi.android.wanandroid.util.UpdateUtils
import com.tainzhi.android.wanandroid.util.gone
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_drawer_nav_content_layout.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class MainActivity : BaseVMActivity<MainViewModel>(useBinding = true) {

    private lateinit var navController: NavController

    //    private var exitTime = 0L
    private val mainDestinationIds = setOf(R.id.mainFragment, R.id.blogFragment, R.id
            .projectFragment)

    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {
    
        navController = mainNavHostFragment.findNavController()

        val appBarConfig = AppBarConfiguration(mainDestinationIds, mainDrawerLayout)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfig)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in mainDestinationIds) {
                mainBottomNavigation.visible()
                toolbar.visible()
                mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                mainBottomNavigation.gone()
                toolbar.gone()
                mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, mainDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close)
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mainBottomNavigation.run {
            setupWithNavController(navController)
            setOnNavigationItemSelectedListener { menuItem ->
                NavigationUI.onNavDestinationSelected(menuItem, navController)
            }
        }

        initNavigationView()

    }

    override fun initVM(): MainViewModel = getViewModel()

    override fun startObserve() {

        // update theme
        WanApp.preferenceRepository.nightMode.observe(this, Observer { nightMode ->
            nightMode?.let { delegate.localNightMode = it }
        })

        mViewModel.updateInfo.observe(this, Observer { updateInfo ->
            UpdateUtils.newInstance().updateApp(this, updateInfo)
        })
    }

    private fun initNavigationView() {
        mainDrawerLayoutNavigation.setupWithNavController(navController)
        userNameTv.setOnClickListener {
            navController.navigate(R.id.action_main_to_login)
        }
        userImageIv.setOnClickListener {
            navController.navigate(R.id.action_main_to_login)
        }
        myCollectionBtn.setOnClickListener {
            navController.navigate(R.id.collectFragment)
        }
        browseHistoryBtn.setOnClickListener {
            navController.navigate(R.id.historyFragment)
        }
        settingsBtn.setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }
        logoutBtn.setOnClickListener {
            toast(mViewModel.user.value?.nickname + getString(R.string.logout_success))
            mViewModel.logout()
            mainDrawerLayout.closeDrawers()
        }
    }

    override fun initData() {
    
        (mBinding as ActivityMainBinding).include.viewModel = mViewModel
    
        // FIXME: 2020/3/11 初始化没有User的post，导致用户信息没有显示，原因还是用户登录状态的保持和传递
        // 尤其是没有使用Event bus，怎么处理，还需要深入
        mViewModel.getAppUpdateInfo()
    
        Timber.tag(MainActivity::class.simpleName)
        Timber.i(mViewModel.user.value.toString())
    }

    override fun onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // if (System.currentTimeMillis() - exitTime > 2000) {
            //     exitTime = System.currentTimeMillis()
            //     toast(R.string.back_press_hint)
            // } else {
            //     super.onBackPressed()
            // }
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.searchFragment) {
            navController.navigate(R.id.searchFragment)
        }
        return super.onOptionsItemSelected(item)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(Navigation.findNavController(this, R.id.mainNavHostFragment), mainDrawerLayout)
    }
    
}
