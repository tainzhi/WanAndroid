package com.tainzhi.android.wanandroid.view.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import com.tainzhi.android.wanandroid.WanApp
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension
import com.tencent.smtt.export.external.interfaces.IX5WebSettings
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.*
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/27 上午7:14
 * @description:
 **/

class X5WebView : FrameLayout {
    
    private var webView: WebView? = null
    private lateinit var progressBar: ProgressBar
    
    var onPageTitleCallback: OnPageTitleCallback? = null
    var onPageLoadCallback: OnPageLoadCallback? = null
    var onPageProgressCallback: OnPageProgressCallback? = null
    var onHistoryUpdateCallback: OnHistoryUpdateCallback? = null
    
    var isProgressBarShown = true
    var isDarkTheme = false
    var maskColor = Color.TRANSPARENT
    
    
    constructor(context: Context) : super(context) {
        initialization(context)
    }
    
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialization(context)
    }
    
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialization(context)
    }
    
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initialization(context)
    }
    
    private fun initialization(context: Context) {
        // context.getWindow().setFormat(PixelFormat.TRANSLUCENT)
        webView = WebView(context)
        addView(webView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        progressBar.max = 100
        addView(progressBar, LayoutParams(LayoutParams.MATCH_PARENT,
                                          TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics).toInt()))
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        }
        webView!!.overScrollMode = WebView.OVER_SCROLL_NEVER
        webView!!.view.overScrollMode = View.OVER_SCROLL_NEVER
        webView!!.webChromeClient = MyWebChromeClient()
        webView!!.webViewClient = MyWebViewClient()
        val webSetting: WebSettings = webView!!.settings
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(false)
        webSetting.builtInZoomControls = false
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        webSetting.loadWithOverviewMode = true
        webSetting.setAppCacheEnabled(true)
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        // 不能被注释掉，否则无法查看公众号文章
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = false
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webSetting.cacheMode = WebSettings.LOAD_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        val ext: IX5WebSettingsExtension? = webView?.settingsExtension
        ext?.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY)
        if (isDarkTheme) {
            maskColor = alphaColor(Color.parseColor("#f5f5f5"), 0.8f)
            ext?.setDayOrNight(false)
        } else {
            ext?.setDayOrNight(true)
        }
    }
    
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (isDarkTheme) {
            canvas.drawColor(maskColor)
        }
    }
    
    fun loadUrl(url: String): X5WebView {
        webView!!.loadUrl(url)
        return this
    }
    
    fun getUrl() = webView!!.url ?: ""
    
    fun getTitle() = webView!!.title ?: ""
    
    private fun alphaColor(color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
        return Color.argb(
                (alpha * 255).toInt(),
                color,
                color,
                color
        )
    }
    
    fun canGoBack() = webView!!.canGoBack()
    
    fun canGoForward() = webView!!.canGoForward()
    
    fun canGoBackOrForward(steps: Int) = webView!!.canGoBackOrForward(steps)
    
    fun goBack() {
        webView!!.goBack()
    }
    
    fun goForward() {
        webView!!.goForward()
    }
    
    fun goBackOrForward(steps: Int) {
        webView!!.goBackOrForward(steps)
    }
    
    fun reload() {
        webView!!.reload()
    }
    
    fun stopLoading() {
        webView!!.stopLoading()
    }
    
    fun onPause() {
        webView!!.onPause()
    }
    
    fun onResume() {
        webView!!.onResume()
    }
    
    fun onDestroy() {
        removeView(webView)
        webView!!.removeAllViews()
        
        webView!!.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        webView!!.stopLoading()
        webView!!.webChromeClient = null
        webView!!.webViewClient = null
        webView!!.destroy()
    }
    
    fun setOnPageTitleCallback(onPageTitleCallback: OnPageTitleCallback): X5WebView {
        this.onPageTitleCallback = onPageTitleCallback
        return this
    }
    
    fun setOnPageLoadCallback(onPageLoadCallback: OnPageLoadCallback): X5WebView {
        this.onPageLoadCallback = onPageLoadCallback
        return this
    }
    
    fun setOnPageProgressCallback(onPageProgressCallback: OnPageProgressCallback): X5WebView {
        this.onPageProgressCallback = onPageProgressCallback
        return this
    }
    
    fun setOnHistoryUpdateCallback(onHistoryUpdateCallback: OnHistoryUpdateCallback): X5WebView {
        this.onHistoryUpdateCallback = onHistoryUpdateCallback
        return this
    }
    
    inner class MyWebChromeClient : WebChromeClient() {
        
        override fun onReceivedTitle(p0: WebView?, title: String?) {
            super.onReceivedTitle(p0, title)
            onPageTitleCallback?.onReceivedTitle(title)
        }
        
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress < 95) {
                if (!isProgressBarShown) {
                    isProgressBarShown = true
                    onShowProgress()
                }
                onProgressChanged(newProgress)
            } else {
                onProgressChanged(newProgress)
                if (isProgressBarShown) {
                    isProgressBarShown = false
                    onHideProgress()
                }
            }
        }
        
        private fun onShowProgress() {
            progressBar.visibility = View.VISIBLE
            setProgress(0)
            onPageProgressCallback?.onShowProgress()
        }
        
        private fun onProgressChanged(progress: Int) {
            setProgress(progress)
            onPageProgressCallback?.onProgressChanged(progress)
        }
        
        private fun onHideProgress() {
            progressBar.visibility = View.GONE
            setProgress(100)
            onPageProgressCallback?.onHideProgress()
        }
        
        private fun setProgress(progress: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar.setProgress(progress, true)
            } else {
                progressBar.progress = progress
            }
        }
    }
    
    inner class MyWebViewClient : WebViewClient() {
        private fun shouldInterceptRequest(uri: Uri): Boolean {
            syncCookiesForWanAndroid(uri.toString())
            return false
        }
        //
        // private fun shouldOverrideUrlLoading(uri: Uri): Boolean {
        //     return when (SettingUtils.getInstance().getUrlInterceptType()) {
        //         HostInterceptUtils.TYPE_NOTHING -> false
        //         HostInterceptUtils.TYPE_ONLY_WHITE -> !HostInterceptUtils.isWhiteHost(uri.host)
        //         HostInterceptUtils.TYPE_INTERCEPT_BLACK -> HostInterceptUtils.isBlackHost(uri.host)
        //         else -> false
        //     }
        // }
        
        private fun syncCookiesForWanAndroid(url: String) {
            if (TextUtils.isEmpty(url)) {
                return
            }
            val host = Uri.parse(url).host
            if (!TextUtils.equals(host, "www.wanandroid.com")) {
                return
            }
            val cookies: List<Cookie> = WanApp.cookieJar.loadForRequest(url.toHttpUrl())
            if (cookies.isEmpty()) {
                return
            }
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeSessionCookie()
                cookieManager.removeExpiredCookie()
            } else {
                cookieManager.removeSessionCookies(null)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                for (cookie in cookies) {
                    cookieManager.setCookie(url, cookie.name + "=" + cookie.value)
                }
                CookieSyncManager.createInstance(WanApp.CONTEXT)
                CookieSyncManager.getInstance().sync()
            } else {
                for (cookie in cookies) {
                    cookieManager.setCookie(url, cookie.name + "=" + cookie.value)
                }
                cookieManager.flush()
            }
        }
    
        override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
            return if (shouldInterceptRequest(Uri.parse(url))) {
                WebResourceResponse(null, null, null)
            } else super.shouldInterceptRequest(view, url)
        }
    
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
            return if (shouldInterceptRequest(request.url)) {
                WebResourceResponse(null, null, null)
            } else super.shouldInterceptRequest(view, request)
        }
    
        // override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        //     return shouldOverrideUrlLoading(Uri.parse(url))
        // }
        //
        // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        // override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        //     return shouldOverrideUrlLoading(request.url)
        // }
    
        override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
            super.onPageStarted(p0, p1, p2)
            onPageTitleCallback?.onReceivedTitle(getUrl())
            onPageLoadCallback?.onPageStarted()
        }
        
        override fun onPageFinished(p0: WebView?, p1: String?) {
            super.onPageFinished(p0, p1)
            onPageTitleCallback?.onReceivedTitle(getTitle())
            onPageLoadCallback?.onPageFinished()
        }
        
        override fun doUpdateVisitedHistory(p0: WebView?, p1: String?, p2: Boolean) {
            super.doUpdateVisitedHistory(p0, p1, p2)
            onHistoryUpdateCallback?.onHistoryUpdate(p2)
        }
    }
    
    
    interface OnPageTitleCallback {
        fun onReceivedTitle(title: String?)
    }
    
    interface OnPageLoadCallback {
        fun onPageStarted()
        fun onPageFinished()
    }
    
    interface OnPageProgressCallback {
        fun onShowProgress()
        fun onProgressChanged(progress: Int)
        fun onHideProgress()
    }
    
    interface OnHistoryUpdateCallback {
        fun onHistoryUpdate(isReload: Boolean)
    }
}