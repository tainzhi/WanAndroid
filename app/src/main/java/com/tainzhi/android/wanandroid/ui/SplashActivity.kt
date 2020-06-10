package com.tainzhi.android.wanandroid.ui

import android.text.Html
import android.view.WindowManager
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.SplashAdapter
import com.tainzhi.android.common.base.ui.BaseActivity
import com.tainzhi.android.common.util.fromN
import com.tainzhi.android.common.util.startKtxActivity
import com.tainzhi.android.wanandroid.view.ScrollLinearLayoutManager
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val imageSrcId = arrayListOf(
            R.drawable.android_0,
            R.drawable.android_1,
            R.drawable.android_2,
            R.drawable.android_3,
            R.drawable.android_4,
            R.drawable.android_5)
    private val splashAdapter by lazy { SplashAdapter(imageSrcId.shuffled()) }

    override fun getLayoutResId() = R.layout.activity_splash

    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        splashRecyclerView.run {
            adapter = splashAdapter
            layoutManager = ScrollLinearLayoutManager(this@SplashActivity)
            smoothScrollToPosition(Integer.MAX_VALUE / 2)
        }
        splashLogoTv.text = if (fromN()) Html.fromHtml(getString(R.string.app_logo_html), Html.FROM_HTML_MODE_LEGACY)
        else Html.fromHtml(getString(R.string.app_logo_html))

    }

    override fun initData() {
        launch {
            delay(2000L)
            splashRecyclerView.stopScroll()
            finish()
            startKtxActivity<MainActivity>()
        }
    }

    override fun finish() {
        // bug: 从全面屏切换到非全面屏，状态栏会闪烁
        // 方法1：退出前属性设置非全面屏。这种虽然貌似能解决问题，但是这样切换会导致你的
        //       Activity在从全屏变化为非全屏时无法适应主题的变化，而被切掉一块
        // window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        //        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        // 方法2：清除全屏属性
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.finish()
    }
}