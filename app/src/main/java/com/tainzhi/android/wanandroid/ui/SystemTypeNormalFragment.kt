package com.tainzhi.android.wanandroid.ui

import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseFragment
import com.tainzhi.android.wanandroid.bean.SystemParent
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_system_type_normal.*

class SystemTypeNormalFragment : BaseFragment() {

    private val args: SystemTypeNormalFragmentArgs by navArgs()
    private lateinit var systemParent: SystemParent

    override fun getLayoutResId() = R.layout.fragment_system_type_normal

    override fun initView() {


        requireActivity().onBackPressedDispatcher.addCallback { onBack() }

        systemParent = args.articleList
        toolbar.run {
            title = systemParent.name
        
            setNavigationOnClickListener {
                onBack()
            }
        }
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        initViewPager()
    }


    override fun initData() {
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

    private fun onBack() {
        findNavController().popBackStack()
    }
}