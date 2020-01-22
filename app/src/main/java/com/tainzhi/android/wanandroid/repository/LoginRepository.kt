package com.tainzhi.android.wanandroid.repository

import com.google.gson.Gson
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.api.WanService
import com.tainzhi.android.wanandroid.base.BaseRepository
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.util.Preference

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/21 下午3:27
 * @description:
 **/

class LoginRepository(val service: WanService): BaseRepository() {
    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val userJson by Preference(Preference.USER_GSON, "")

    suspend fun login(userName: String, passWord: String): Result<User> {
        return safeApiCall(call = {request})
    }

    private suspend fun requestLogin(userName: String, passWord: String): Result<User> {
        val response = service.login(userName, passWord)

        return executeRespons(response, {
            val user = response.data
            isLogin = true
            userJson = Gson().toJson(user)
            WanApp.current_user = user
        })
    }

    private suspend fun requestRegister(userName:String, passWord: String): Result<User> {
        val response = service.register(userName, passWord, passWord)
        return executeRespons(response, {requestLogin(userName, passWord)})
    }
}