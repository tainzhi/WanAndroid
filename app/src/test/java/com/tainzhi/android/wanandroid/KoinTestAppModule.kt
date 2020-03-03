package com.tainzhi.android.wanandroid

import android.app.Application
import androidx.room.Room
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.db.WanAppDB
import com.tainzhi.android.wanandroid.di.repositoryModule
import com.tainzhi.android.wanandroid.di.viewModelModule
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/3 上午8:25
 * @description:
 **/

// 替换掉默认的 Dispatchers.default, 它不能用于测试
val testCoroutineModule = module {
    single { CoroutinesDispatcherProvider(Dispatchers.Main, Dispatchers.Main, Dispatchers.Main) }
}

val testDatabaseModule = module {
    fun provideDatabase(application: Application): WanAppDB {
        return Room
                .inMemoryDatabaseBuilder(application, WanAppDB::class.java)
                .build()
    }

    fun provideHistoryDao(database: WanAppDB): HistoryDao {
        return database.historyDao()
    }
    single { provideDatabase(androidApplication()) }
    single { provideHistoryDao(get()) }
}

val testAppModule = listOf(
        viewModelModule,
        repositoryModule,
        testDatabaseModule,
        testCoroutineModule)

