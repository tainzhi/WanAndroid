package com.tainzhi.android.wanandroid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.util.Preference

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/12 上午7:27
 * @description:
 **/

class MainActivityViewModel() : BaseViewModel() {

    private var _isLogin by Preference(Preference.IS_LOGIN, false)
    private var _user by Preference(Preference.USER_GSON, "")

    private val mIsLogin: MutableLiveData<Boolean> = MutableLiveData(true)
    private val mUser: MutableLiveData<User> = MutableLiveData(getUserFromGson())

    val isLogin: LiveData<Boolean>
        get() = mIsLogin
    val user: LiveData<User>
        get() = mUser

    private fun getUserFromGson(): User = Gson().fromJson<User>(_user, User::class.java)

    fun update() {
        mIsLogin.value = true
        mUser.value = getUserFromGson()
    }

}