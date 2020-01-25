package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.wanandroid.base.BaseRepository
import com.tainzhi.android.wanandroid.base.Result

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午9:29
 * @description:
 **/


class ShareRepository : BaseRepository() {


    suspend fun shareArticle(title: String, url: String): Result<String> {
        return safeApiCall(call = { requestShareArticle(title, url) }, errorMessage = "分享失败")
    }


    private suspend fun requestShareArticle(title: String, url: String): Result<String> =
            executeResponse(WanClient.service.shareArticle(title, url))
}
