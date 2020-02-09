package com.tainzhi.android.wanandroid.ui

import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {
        val navController = findNavController(R.id.main_nav_host_fragment)
        val appBarConfig = AppBarConfiguration.Builder(
                R.id.homeFragment2,
                R.id.blogFragment2,
                R.id.projectFragment2
        ).setDrawerLayout(main_drawer)
                .build()
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfig)
        main_bottom_nav.setupWithNavController(navController)
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        if (main_drawer.isDrawerOpen(GravityCompat.START)) {
            main_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
