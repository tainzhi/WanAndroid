package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.common.base.BaseRepository
import com.tainzhi.android.common.base.Result
import com.tainzhi.android.wanandroid.bean.ArticleList
import com.tainzhi.android.wanandroid.bean.SystemParent

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午9:27
 * @description:
 **/

class ProjectRepository : BaseRepository() {

    suspend fun getProjectTypeDetailList(page: Int, cid: Int): Result<ArticleList> {
        return safeApiCall(call = {requestProjectTypeDetailList(page, cid)},errorMessage = "发生未知错误")
    }

    suspend fun getLastedProject(page: Int): Result<ArticleList> {
        return safeApiCall(call = {requestLastedProject(page)},errorMessage = "发生未知错误")
    }

    suspend fun getProjectTypeList(): Result<List<SystemParent>> {
        return safeApiCall(call = { requestProjectTypeList() })
    }

    suspend fun getBlog(): Result<List<SystemParent>> {
        return safeApiCall(call = { requestBlogTypeList() })
    }

    private suspend fun requestProjectTypeDetailList(page: Int, cid: Int) =
            executeResponse(WanClient.service.getProjectTypeDetail(page, cid))

    private suspend fun requestLastedProject(page: Int):Result<ArticleList> =
            executeResponse(WanClient.service.getLastedProject(page))

    private suspend fun requestProjectTypeList() =
            executeResponse(WanClient.service.getProjectType())

    private suspend fun requestBlogTypeList() =
            executeResponse(WanClient.service.getBlogType())

}
