package com.tainzhi.android.wanandroid.ui.project

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.bean.SystemParent
import com.tainzhi.android.wanandroid.ui.ProjectTypeFragment
import com.tainzhi.android.wanandroid.ui.SystemTypeFragment
import kotlinx.android.synthetic.main.fragment_project.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

open class ProjectFragment : BaseVMFragment<ProjectViewModel>() {

    private val mProjectTypeList = mutableListOf<SystemParent>()
    open var isBlog = false // 区分是公众号还是项目分类

    override fun getLayoutResId() = R.layout.fragment_project

    override fun initView() {
        initViewPager()
    }

    override fun initVM(): ProjectViewModel = getViewModel()

    override fun initData() {
//        projectToolbar.setNavigationOnClickListener { onBackPressed() }

        if (isBlog) mViewModel.getBlogType()
        else mViewModel.getProjectTypeList()

    }

    private fun initViewPager() {

        projectViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = mProjectTypeList.size

            override fun createFragment(position: Int) = chooseFragment(position)

        }

        TabLayoutMediator(tabLayout, projectViewPager) { tab, position ->
            tab.text = mProjectTypeList[position].name
        }.attach()
    }

    private fun chooseFragment(position: Int): Fragment {
        return if (isBlog) SystemTypeFragment.newInstance(mProjectTypeList[position].id, true)
        else ProjectTypeFragment.newInstance(mProjectTypeList[position].id, false)
    }

    private fun getProjectTypeList(projectTypeList: List<SystemParent>) {
        mProjectTypeList.clear()
        mProjectTypeList.addAll(projectTypeList)
        projectViewPager.adapter?.notifyDataSetChanged()
    }

    override fun startObserve() {
        mViewModel.systemData.observe(this, Observer {
            it?.run { getProjectTypeList(it) }
        })
    }

}