package com.tainzhi.android.wanandroid

import android.app.Application
import android.content.Context
import cat.ereza.customactivityoncrash.config.CaocConfig
// import com.didichuxing.doraemonkit.DoraemonKit
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.di.appModule
import com.tainzhi.android.wanandroid.repository.PreferenceRepository
import com.tainzhi.android.wanandroid.ui.CrashActivity
import com.tainzhi.android.wanandroid.ui.MainActivity
import com.tainzhi.android.wanandroid.util.ReleaseCrashTimberTree
// import com.tencent.bugly.crashreport.CrashReport
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
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
        val cookieJar by lazy {
            PersistentCookieJar(SetCookieCache(),
                                SharedPrefsCookiePersistor(CONTEXT))
        }
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
    
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseCrashTimberTree())
        }
    
        initCrashActivity()
        initBugly()

        // release版本无法使用 DoraemonKit
        // DoraemonKit.install(this)
    }
    
    private fun initCrashActivity() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                .enabled(true)
                .showErrorDetails(true)
                .showRestartButton(true)
                .logErrorOnRestart(false)
                .trackActivities(false)
                .minTimeBetweenCrashesMs(2000)
                .restartActivity(MainActivity::class.java)
                .errorActivity(CrashActivity::class.java)
                .apply()
    }
    
    private fun initBugly() {
        // CrashReport.initCrashReport(applicationContext, "25c0753a52", BuildConfig.DEBUG)
    }
    
}