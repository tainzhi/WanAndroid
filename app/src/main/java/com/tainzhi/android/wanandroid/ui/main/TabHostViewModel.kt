package com.tainzhi.android.wanandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.wanandroid.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.UpdateInfo
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.repository.LoginRepository
import com.tainzhi.android.wanandroid.repository.MainRepository
import com.tainzhi.android.wanandroid.util.MemoryCache
import com.tainzhi.android.wanandroid.util.Preference
import com.tainzhi.android.wanandroid.util.UpdateUtils

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/10 上午9:30
 * @description:
 **/

class TabHostViewModel(
        private val loginRepository: LoginRepository,
        private val mainRepository: MainRepository,
        private val historyDao: HistoryDao,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {
    
    private val mIsLogin: MutableLiveData<Boolean> = WanApp.preferenceRepository.mIsLogin
    private val mUser: MutableLiveData<User> = WanApp.preferenceRepository.mUser
    private val mUpdateInfo = MutableLiveData<UpdateInfo>()

    val isLogin: LiveData<Boolean> = mIsLogin
    val user: LiveData<User> = mUser
    val updateInfo = mUpdateInfo


    fun getAppUpdateInfo() {
        launch {
            val result = mainRepository.getUpdateInfo()
            if (result is Result.Success) {
                val updateInfo = result.data
                MemoryCache.instance?.put(MemoryCache.KEY_UPDATE_INFO, updateInfo)
                val versionCode = updateInfo.versionCode
                val updateUtils = UpdateUtils.newInstance()
                if (!updateUtils.isTodayChecked()) {
                    if (updateUtils.shouldUpdate(versionCode)) {
                        mUpdateInfo.postValue(updateInfo)
                    }
                }
            }
        }
    }
    
    fun getUserInfo() {
        if (mIsLogin.value == true) {
            launch {
                mUser.postValue(WanApp.preferenceRepository.mUser.value)
            }
        }
    }
    
    fun logout() {
        mIsLogin.value = false
        mUser.value = null
        Preference.clearAll()
        
        launch {
            historyDao.deleteAll()
            loginRepository.logout()
        }
    }
    
}