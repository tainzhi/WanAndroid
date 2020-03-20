package com.tainzhi.android.wanandroid

import ReleaseCrashTimberTree
import android.app.Application
import android.content.Context
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.di.appModule
import com.tainzhi.android.wanandroid.repository.PreferenceRepository
import com.tainzhi.android.wanandroid.ui.CrashActivity
import com.tainzhi.android.wanandroid.ui.MainActivity
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
    
}