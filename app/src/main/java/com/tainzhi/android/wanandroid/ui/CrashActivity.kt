package com.tainzhi.android.wanandroid.ui


import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.common.base.ui.BaseActivity
import com.tainzhi.android.common.util.gone
import com.tainzhi.android.common.util.visible
import kotlinx.android.synthetic.main.activity_crash.*

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/20 下午12:00
 * @description: app崩溃后的页面
 */
class CrashActivity : BaseActivity() {
    
    private var caocConfig: CaocConfig? = null
    
    override fun getLayoutResId() = R.layout.activity_crash
    
    override fun initView() {
        checkLogTv.setOnClickListener {
            checkLogTv.gone()
            bugIv.gone()
            logTv.visible()
            logTv.text = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
        }
        finishAppBtn.setOnClickListener {
            CustomActivityOnCrash.closeApplication(this, caocConfig!!)
        }
        restartAppBtn.setOnClickListener {
            CustomActivityOnCrash.restartApplication(this, caocConfig!!)
        }
    }
    
    override fun initData() {
        caocConfig = CustomActivityOnCrash.getConfigFromIntent(intent)
        if (caocConfig == null) {
            finish()
            return
        }
    }
    
}