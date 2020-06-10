package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.api.WanClient
import com.tainzhi.android.common.base.BaseRepository
import com.tainzhi.android.common.base.Result
import com.tainzhi.android.wanandroid.bean.Navigation

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午9:25
 * @description:
 **/

class NavigationRepository : BaseRepository() {


    suspend fun getNavigation(): Result<List<Navigation>> {
        return safeApiCall(call = { requestNavigation() }, errorMessage = "获取数据失败")
    }


    private suspend fun requestNavigation(): Result<List<Navigation>> =
            executeResponse(WanClient.service.getNavigation())
}
