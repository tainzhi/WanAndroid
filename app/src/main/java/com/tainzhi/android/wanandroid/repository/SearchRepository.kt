package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.wanandroid.base.BaseRepository
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.bean.ArticleList
import com.tainzhi.android.wanandroid.bean.Hot

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午9:28
 * @description:
 **/


class SearchRepository : BaseRepository() {
    suspend fun searchHot(page: Int, key: String): Result<ArticleList> {
        return safeApiCall(call = { requestSearch(page, key) })
    }

    suspend fun getWebSites(): Result<List<Hot>> {
        return safeApiCall(call = { requestWebSites() })
    }

    suspend fun getHotSearch(): Result<List<Hot>> {
        return safeApiCall(call = { requestHotSearch() })
    }

    private suspend fun requestWebSites():Result<List<Hot>> =
            executeResponse(WanClient.service.getWebsites())

    private suspend fun requestHotSearch():Result<List<Hot>> =
            executeResponse(WanClient.service.getHot())

    private suspend fun requestSearch(page: Int, key: String):Result<ArticleList> =
            executeResponse(WanClient.service.searchHot(page, key))
}
