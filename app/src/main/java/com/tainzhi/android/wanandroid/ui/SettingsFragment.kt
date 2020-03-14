package com.tainzhi.android.wanandroid.ui

import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.base.ui.BaseFragment
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_settings

    override fun initView() {
        toolbar.setTitle(R.string.settings)
        toolbar.setNavigationOnClickListener { onBack() }

        requireActivity().onBackPressedDispatcher.addCallback { onBack() }
    }

    override fun initData() {
        changeThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            WanApp.preferenceRepository.darkTheme = isChecked
        }
        WanApp.preferenceRepository.isDarkTheme.observe(this, Observer { isDarkTheme ->
            isDarkTheme?.let { changeThemeSwitch.isChecked = it }
        })

        settingsAboutFl.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
    }

    private fun onBack() {
        findNavController().popBackStack()
    }
}

