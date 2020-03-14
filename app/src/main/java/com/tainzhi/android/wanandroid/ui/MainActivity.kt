package com.tainzhi.android.wanandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.WanApp

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/14 下午12:00
 * @description:
 **/

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // update theme
        WanApp.preferenceRepository.nightMode.observe(this, Observer { nightMode ->
            nightMode?.let { delegate.localNightMode = it }
        })
    }
}