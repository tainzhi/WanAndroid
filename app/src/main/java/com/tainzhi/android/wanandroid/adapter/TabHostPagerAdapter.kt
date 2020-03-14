package com.tainzhi.android.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/14 下午1:36
 * @description:
 **/

class TabHostPagerAdapter(fragment: Fragment, private val fragmentList: List<Fragment>) :
        FragmentStateAdapter
        (fragment) {
    override fun getItemCount() = fragmentList.size
    
    override fun createFragment(position: Int) = fragmentList[position]
    
}