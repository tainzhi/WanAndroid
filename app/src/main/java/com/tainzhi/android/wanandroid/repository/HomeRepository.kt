package com.tainzhi.android.wanandroid.repository

import com.bumptech.glide.request.RequestListener
import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.wanandroid.base.BaseRepository
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.bean.ArticleList
import com.tainzhi.android.wanandroid.bean.Banner

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/24 下午6:32
 * @description:
 **/

class HomeRepository: BaseRepository() {
    suspend fun getBanners(): Result<List<Banner>> {
        return safeApiCall(call = { requestBanners() }, errorMsg = "")
    }

    private suspend fun requestBanners() : Result<List<Banner>> =
        executeResponse(WanClient.service.getBanner())

    suspend fun getArticle(page: Int) : Result<ArticleList> {
        return safeApiCall(call = { requestArticleList(page)}, errorMsg = "")
    }

    private suspend fun requestArticleList(page : Int) : Result<ArticleList> {
        executeResponse(WanClient.service.getHomeArticles(page))
    }

}