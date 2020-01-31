package com.tainzhi.android.wanandroid.ui

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseActivity
import com.tainzhi.android.wanandroid.view.X5WebView
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_browser.*
import kotlinx.android.synthetic.main.title_layout.*


class BrowserActivity : BaseActivity() {

    companion object {
        const val URL = "url"
    }

    private lateinit var webView: X5WebView

    override fun getLayoutResId() = R.layout.activity_browser

    override fun initView() {
        toolbar.setTitle(R.string.is_loading)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        progressBar.progressDrawable = this.resources
                .getDrawable(R.drawable.color_progressbar, null)

        initWebView()

        browserLinearLayout.addView(webView)
    }

    override fun initData() {
        intent?.extras?.getString(URL).let {
            webView.loadUrl(it)
        }
    }

    private fun initWebView() {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        webView = X5WebView(applicationContext)
        webView.run {
            layoutParams = params

            webViewClient = object : WebViewClient() {

                override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                    super.onPageStarted(p0, p1, p2)
                    progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(p0: WebView?, p1: String?) {
                    super.onPageFinished(p0, p1)
                    progressBar.visibility = View.GONE
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    super.onProgressChanged(p0, p1)
                    progressBar.progress = p1
                    Log.e("browser", p1.toString())
                }

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    p1?.let { toolbar.title = p1 }
                }

            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }

    override fun onDestroy() {
        webView.destroy()
        (webView.parent as LinearLayout).removeView(webView)
        super.onDestroy()
    }
}