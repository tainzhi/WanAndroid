package com.tainzhi.android.wanandroid.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.wanandroid.util.Preference


/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/21 上午11:21
 * @description:
 **/

class PreferenceRepository {
    private var _s_nightMode by Preference(Preference.THEME_MODE, Preference.DEFAULT_THEM_MODE)

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

    private val _isDarkTheme: MutableLiveData<Boolean> = MutableLiveData()
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

}