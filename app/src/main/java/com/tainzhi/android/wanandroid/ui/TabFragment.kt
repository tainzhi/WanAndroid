package com.tainzhi.android.wanandroid.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_tab.*


class TabFragment : BaseFragment() {

    private val fragmentList = arrayListOf<Fragment>()
    private val mainFragment by lazy { MainFragment() }

    init {
        fragmentList.run {
            add(mainFragment)
        }
    }

    override fun getLayoutResId() = R.layout.fragment_tab

    override fun initView() {
        initViewPager()
    }


    override fun initData() {
    }

    private val onNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId) {
            R.id.home -> switchFragment(0)
            R.id.blog -> switchFragment(1)
            R.id.search -> switchFragment(2)
            R.id.project -> switchFragment(3)
            R.id.profile -> switchFragment(4)
        }
        true
    }

    private fun switchFragment(position: Int): Boolean {
        viewPager.setCurrentItem(position, false)
        return true
    }

    private fun initViewPager() {
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragmentList.size

            override fun createFragment(position: Int) = fragmentList[position]
        }
    }
}
