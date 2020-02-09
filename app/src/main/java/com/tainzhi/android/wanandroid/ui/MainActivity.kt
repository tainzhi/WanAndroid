package com.tainzhi.android.wanandroid.ui

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseActivity
import com.tainzhi.android.wanandroid.util.gone
import com.tainzhi.android.wanandroid.util.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mainDestionIds = setOf(R.id.homeFragment, R.id.blogFragment, R.id.projectFragment)

    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {
        val navController = findNavController(R.id.main_nav_host_fragment)

        val appBarConfig = AppBarConfiguration(mainDestionIds, main_drawer_layout)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfig)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id in mainDestionIds) {
                main_bottom_nav.visible()
                toolbar.visible()
//                main_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            } else {
                main_bottom_nav.gone()
                toolbar.gone()
//                main_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, main_drawer_layout, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close)
        main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        main_drawer_nav_view.setupWithNavController(navController)

        main_bottom_nav.run {
            setupWithNavController(navController)
            setOnNavigationItemSelectedListener { menuItem ->
                NavigationUI.onNavDestinationSelected(menuItem, navController)
            }
        }

    }

    override fun initData() {
    }

    override fun onBackPressed() {
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
