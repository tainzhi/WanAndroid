package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.wanandroid.base.BaseRepository
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.bean.UpdateInfo

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/10 上午10:28
 * @description:
 **/

class MainRepository : BaseRepository() {
    suspend fun getUpdateInfo(): Result<UpdateInfo> {
        return safeApiCall(call = { requestUpdateInfo() }, errorMessage = "")
    }

    private suspend fun requestUpdateInfo(): Result<UpdateInfo> =
            executeResponse(WanClient.service.getUpdateInfo())
}