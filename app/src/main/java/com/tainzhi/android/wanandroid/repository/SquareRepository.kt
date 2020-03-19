package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.wanandroid.base.BaseRepository
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.bean.ArticleList

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午9:30
 * @description:
 **/


class SquareRepository :  BaseRepository(){
    
    
    suspend fun getSquareArticleList(page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestSquareArticleList(page) })
    }
    
    private suspend fun requestSquareArticleList(page: Int): Result<ArticleList> =
            executeResponse(WanClient.service.getSquareArticleList(page))
}
