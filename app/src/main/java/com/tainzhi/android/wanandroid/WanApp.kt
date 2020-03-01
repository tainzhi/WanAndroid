package com.tainzhi.android.wanandroid

import android.app.Application
import android.content.Context
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.di.appModule
import com.tainzhi.android.wanandroid.repository.PreferenceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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
        lateinit var preferenceRepository: PreferenceRepository
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        startKoin {
            androidContext(this@WanApp)
            androidLogger()
            modules(appModule)
        }

        preferenceRepository = PreferenceRepository()
    }
    //x5内核初始化接口
//        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
//
//            override fun onViewInitFinished(arg0: Boolean) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is $arg0")
//            }
//
//            override fun onCoreInitFinished() {
//            }
//        })
}