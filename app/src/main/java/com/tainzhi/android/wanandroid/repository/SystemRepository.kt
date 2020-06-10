package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.common.base.BaseRepository
import com.tainzhi.android.common.base.Result
import com.tainzhi.android.wanandroid.bean.ArticleList
import com.tainzhi.android.wanandroid.bean.SystemParent

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午9:31
 * @description:
 **/


class SystemRepository : BaseRepository() {

    suspend fun getSystemTypeDetail(cid: Int, page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestSystemTypeDetail(cid, page) })
    }

    suspend fun getSystemTypes(): Result<List<SystemParent>> {
        return safeApiCall(call = { requestSystemTypes() })
    }

    suspend fun getBlogArticle(cid: Int, page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestBlogArticle(cid, page) })
    }

    private suspend fun requestSystemTypes(): Result<List<SystemParent>> =
            executeResponse(WanClient.service.getSystemType())

    private suspend fun requestSystemTypeDetail(cid: Int,page: Int): Result<ArticleList> =
            executeResponse(WanClient.service.getSystemTypeDetail(page, cid) )

    private suspend fun requestBlogArticle(cid: Int,page: Int): Result<ArticleList> =
            executeResponse(WanClient.service.getSystemTypeDetail(page, cid) )
}
