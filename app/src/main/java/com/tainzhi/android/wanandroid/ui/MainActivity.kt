package com.tainzhi.android.wanandroid.ui

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseVMActivity
import com.tainzhi.android.wanandroid.databinding.ActivityMainBinding
import com.tainzhi.android.wanandroid.ui.login.LoginViewModel
import com.tainzhi.android.wanandroid.util.gone
import com.tainzhi.android.wanandroid.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_drawer_nav_content_layout.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseVMActivity<LoginViewModel>(useBinding = true) {

    private lateinit var navController: NavController

    private val mainDestionIds = setOf(R.id.mainFragment, R.id.blogFragment, R.id.projectFragment)

    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {

        navController = findNavController(R.id.mainNavHostFragment)

        val appBarConfig = AppBarConfiguration(mainDestionIds, mainDrawerLayout)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfig)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in mainDestionIds) {
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

    override fun initVM(): LoginViewModel = getViewModel()

    override fun startObserve() {
    }

    private fun initNavigationView() {
        mainDrawerLayoutNavigation.setupWithNavController(navController)
        userImage.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_loginFragment)
        }
        myCollection.setOnClickListener { navController.navigate(R.id.action_mainFragment_to_collectFragment) }
        browseHistory.setOnClickListener {
            navController.navigate(R.id
                    .action_mainFragment_to_historyFragment)
        }
        otherInfo.setOnClickListener {
            navController.navigate(R.id
                    .action_mainFragment_to_otherInfoFragment)
        }
        logout.setOnClickListener {
            viewModel.logout()
            mainDrawerLayout.closeDrawers()
        }
    }

    override fun initData() {
        (mBinding as ActivityMainBinding).include.viewModel = viewModel
    }

    override fun onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.mainNavHostFragment)
        return super.onOptionsItemSelected(item) || item.onNavDestinationSelected(navController)
    }
}
