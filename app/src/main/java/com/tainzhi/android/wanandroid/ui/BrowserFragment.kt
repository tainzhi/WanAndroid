package com.tainzhi.android.wanandroid.ui

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.common.base.ui.BaseFragment
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_browser.*


class BrowserFragment : BaseFragment() {

    private val args: BrowserFragmentArgs by navArgs()

    override fun getLayoutResId() = R.layout.fragment_browser

    override fun initView() {
        toolbar.setTitle(R.string.is_loading)
        toolbar.setNavigationOnClickListener { view -> onBack(view) }
    
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed)
    
        webView.run {
            isDarkTheme = WanApp.preferenceRepository.darkTheme
            setOnPageTitleCallback(object : com.tainzhi.android.wanandroid.view.widget.X5WebView.OnPageTitleCallback {
                override fun onReceivedTitle(title: String?) {
                    toolbar.title = title
                }
            })
            loadUrl(args.url)
        }
    }
    
    override fun initData() {
    }
    
    override fun onResume() {
        super.onResume()
        webView.onResume()
    }
    
    override fun onPause() {
        super.onPause()
        webView.onPause()
    }
    
    private fun onBack(view: View) {
        // 如果可以反回上次浏览网页
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            webView.onDestroy()
            view.findNavController().popBackStack()
        }
    }
    
    private val onBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 如果可以反回上次浏览网页
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                webView.onDestroy()
                findNavController().popBackStack()
            }
        }
    }
}