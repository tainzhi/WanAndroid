package com.tainzhi.android.wanandroid.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.util.Preference


/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/21 上午11:21
 * @description:
 **/

class PreferenceRepository {
    private var _s_nightMode by Preference(Preference.KEY_THEME_MODE, Preference.DEFAULT_THEM_MODE)

    private val _nightMode = MutableLiveData(_s_nightMode)
    val nightMode: LiveData<Int> = _nightMode

    var darkTheme: Boolean = false
        get() = _s_nightMode == AppCompatDelegate.MODE_NIGHT_YES
        set(value) {
            _s_nightMode = if (value) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            _nightMode.value = _s_nightMode
            _isDarkTheme.value = value
            field = value
        }

    private val _isDarkTheme: MutableLiveData<Boolean> = MutableLiveData(darkTheme)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme


    private var _isLogin by Preference(Preference.KEY_IS_LOGIN, false)
    private var _user by Preference(Preference.KEY_USER_JSON, "")

    val mIsLogin: MutableLiveData<Boolean> = MutableLiveData(_isLogin)
    val mUser: MutableLiveData<User> = MutableLiveData(getUserFromJson())

    val isLogin: LiveData<Boolean> = mIsLogin
    val user: LiveData<User> = Transformations.switchMap(mIsLogin) { isLogin ->
        if (isLogin) {
            mUser.value = getUserFromJson()
        } else {
            mUser.value = null
        }
        mUser
    }

    private fun getUserFromJson(): User = Gson().fromJson<User>(_user, User::class.java)
}