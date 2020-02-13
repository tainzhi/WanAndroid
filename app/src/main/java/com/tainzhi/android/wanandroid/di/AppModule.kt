package com.tainzhi.android.wanandroid.di

import android.app.Application
import androidx.room.Room
import com.tainzhi.android.wanandroid.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.wanandroid.api.WanService
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.db.WanAppDB
import com.tainzhi.android.wanandroid.repository.*
import com.tainzhi.android.wanandroid.ui.ArticleViewModel
import com.tainzhi.android.wanandroid.ui.login.LoginViewModel
import com.tainzhi.android.wanandroid.ui.navigation.NavigationViewModel
import com.tainzhi.android.wanandroid.ui.project.ProjectViewModel
import com.tainzhi.android.wanandroid.ui.search.SearchViewModel
import com.tainzhi.android.wanandroid.ui.system.SystemViewModel
import com.tainzhi.android.wanandroid.util.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 14:54
 * @description:
 **/

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { ArticleViewModel(get(), get(), get(), get(), get()) }
    viewModel { SystemViewModel(get(), get()) }
    viewModel { NavigationViewModel(get()) }
    viewModel { ProjectViewModel(get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
}

val repositoryModule = module {
    single { WanClient.getService(WanService::class.java, WanService.BASE_URL) }
    single { CoroutinesDispatcherProvider() }
    single { LoginRepository(get()) }
    single { HomeRepository() }
    single { SquareRepository() }
    single { ProjectRepository() }
    single { CollectRepository() }
    single { SystemRepository() }
    single { NavigationRepository() }
    single { SearchRepository() }
    single { ShareRepository() }
}

val databaseModule = module {
    fun provideDatabase(application: Application): WanAppDB {
        return Room
                .databaseBuilder(application, WanAppDB::class.java, DB_NAME)
                .build()
    }

    fun provideHistoryDao(database: WanAppDB): HistoryDao {
        return database.historyDao()
    }
    single { provideDatabase(androidApplication()) }
    single { provideHistoryDao(get()) }
}

val appModule = listOf(viewModelModule, repositoryModule, databaseModule)

