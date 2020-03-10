package com.tainzhi.android.wanandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.wanandroid.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.repository.LoginRepository
import com.tainzhi.android.wanandroid.util.Preference

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/10 上午9:30
 * @description:
 **/

class MainViewModel(
        val repository: LoginRepository,
        private val historyDao: HistoryDao,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {

    private val mIsLogin: MutableLiveData<Boolean> = WanApp.preferenceRepository.mIsLogin
    private val mUser: MutableLiveData<User> = WanApp.preferenceRepository.mUser

    val isLogin: LiveData<Boolean> = mIsLogin
    val user: LiveData<User> = mUser

    fun logout() {
        mIsLogin.value = false
        mUser.value = null
        Preference.clearAll()

        launch {
            historyDao.deleteAll()
            repository.logout()
        }
    }
}