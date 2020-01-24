package com.tainzhi.android.wanandroid.di

import com.tainzhi.android.wanandroid.CoroutinesDispathcherProvider
import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.wanandroid.api.WanService
import com.tainzhi.android.wanandroid.repository.HomeRepository
import com.tainzhi.android.wanandroid.repository.LoginRepository
import com.tainzhi.android.wanandroid.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 14:54
 * @description:
 **/

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
}

val repositoryModule = module {
    single { WanClient.getService(WanService::class.java, WanService.BASE_URL)}
    single { CoroutinesDispathcherProvider()}
    single { LoginRepository(get()) }
    single { HomeRepository()}
}
val appModule = listOf(viewModelModule, repositoryModule)

