package com.tainzhi.android.wanandroid.api

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.base.BaseRetorfitClient
import com.tainzhi.android.wanandroid.util.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/20 上午6:18
 * @description:
 **/

object WanClient : BaseRetorfitClient() {

    val service by lazy { getService(WanService::class.java, WanService.BASE_URL) }

    private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(),
            SharedPrefsCookiePersistor(WanApp.CONTEXT)) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {

        val httpCacheDirectory = File(WanApp.CONTEXT.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
                .cookieJar(cookieJar)
                .addInterceptor { chain ->
                    var request = chain.request()
                    if (!NetWorkUtils.isNetworkAvailable(WanApp.CONTEXT)) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build()
                    }
                    val response = chain.proceed(request)
                    if (!NetWorkUtils.isNetworkAvailable(WanApp.CONTEXT)) {
                        val maxAge = 60 * 60
                        response.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", "public, max-age=$maxAge")
                                .build()
                    } else {
                        val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                        response.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                                .build()
                    }
                    response
                }
    }
}