package com.tainzhi.android.wanandroid.base

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/20 上午6:07
 * @description:
 **/

abstract class BaseRetorfitClient {

    companion object {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            // FIXME: 2020/1/27  在Android Studio 4.0 preview alwasy false, 这是一个bug
            // 而在正式版上没有问题
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                // FIXME: 2020/1/27
//                logging.level = HttpLoggingInterceptor.Level.NONE
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            builder.addInterceptor(logging)
                    .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

            // FIXME: 2020/1/27  cache有问题，需要解决
//            handleBuilder(builder)

            return builder.build()
        }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .baseUrl(baseUrl)
                .build().create(serviceClass)
    }
}