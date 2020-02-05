package com.tainzhi.android.wanandroid.ui

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseActivity
import com.tainzhi.android.wanandroid.bean.SystemParent
import kotlinx.android.synthetic.main.activity_system_type_normal.*

class SystemTypeNormalActivity : BaseActivity() {

    companion object {
        const val ARTICLE_LIST = "article_list"
    }

    private val systemParent: SystemParent by lazy { intent.getSerializableExtra(ARTICLE_LIST) as SystemParent }

    override fun getLayoutResId() = R.layout.activity_system_type_normal

    override fun initView() {
        toolbar.run {
            title = systemParent.name
            setNavigationIcon(R.drawable.arrow_back)
        }

        initViewPager()
    }


    override fun initData() {
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViewPager() {

        systemDetailViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = systemParent.children.size

            override fun createFragment(position: Int) =
                    SystemTypeFragment.newInstance(systemParent.children[position].id, false)
        }

        TabLayoutMediator(tabLayout, systemDetailViewPager) { tab, position ->
            tab.text = systemParent.children[position].name
        }.attach()

    }
}