package com.tainzhi.android.wanandroid.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/18 14:54
 * @description:
 **/

val viewModelModule = module {
    viewModel { LoginViewModule(get(), get()) }
}

val repositoryModule = module {
    single { LoginRepository(get()) }
}
val appModule = listOf(viewModelModule, repositoryModule)

