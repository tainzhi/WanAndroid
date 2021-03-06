package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.common.base.BaseRepository
import com.tainzhi.android.common.base.Result
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
        return safeApiCall(call = { requestBanners() }, errorMessage = "")
    }

    private suspend fun requestBanners() : Result<List<Banner>> =
        executeResponse(WanClient.service.getBanner())

    suspend fun getArticleList(page: Int) : Result<ArticleList> {
        return safeApiCall(call = { requestArticleList(page)}, errorMessage = "")
    }

    private suspend fun requestArticleList(page : Int) : Result<ArticleList> =
        executeResponse(WanClient.service.getHomeArticles(page))

}