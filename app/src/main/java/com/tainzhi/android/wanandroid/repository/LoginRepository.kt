package com.tainzhi.android.wanandroid.repository

import com.google.gson.Gson
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.api.WanService
import com.tainzhi.android.wanandroid.base.BaseRepository
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.util.Preference

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/21 下午3:27
 * @description:
 **/

class LoginRepository(val service: WanService): BaseRepository() {
    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    suspend fun login(userName: String, passWord: String): Result<User> {
        return safeApiCall(call = { requestLogin(userName, passWord) },
                errorMessage = "登录失败！")
    }

    suspend fun logout() {
        apiCall { service.logOut() }
    }

    private suspend fun requestLogin(userName: String, passWord: String): Result<User> {
        val response = service.login(userName, passWord)

        return executeResponse(response, {
            val user = response.data
            isLogin = true
            userJson = Gson().toJson(user)
            WanApp.current_user = user
        })
    }

    suspend fun register(userName:String, passWord: String): Result<User> {
        val response = service.register(userName, passWord, passWord)
        return executeResponse(response, {requestRegister(userName, passWord)})
    }

    private suspend fun requestRegister(userName: String, passWord: String): Result<User> {
        val response = service.register(userName, passWord, passWord)
        return executeResponse(response, {requestLogin(userName, passWord)})
    }
}