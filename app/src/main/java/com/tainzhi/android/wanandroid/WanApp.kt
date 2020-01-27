package com.tainzhi.android.wanandroid

import android.app.Application
import android.content.Context
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 13:15
 * @description:
 **/

class WanApp : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
        lateinit var current_user: User
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WanApp)
            modules(appModule)
        }
    }
}