package com.tainzhi.android.wanandroid.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.bean.Navigation
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.repository.NavigationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NavigationViewModel(private val navigationRepository: NavigationRepository,
                          private val historyDao: HistoryDao) :
        BaseViewModel() {
    private val _navigationList: MutableLiveData<List<Navigation>> = MutableLiveData()
    val navigationList: LiveData<List<Navigation>>
        get() = _navigationList

    fun getNavigation() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                navigationRepository.getNavigation()
            }
            if (result is Result.Success) {
                _navigationList.value = result.data
            }
        }

    }

    fun insertBrowseHistory(article: Article) {
        launch() {
            withContext(Dispatchers.Default) {
                historyDao.insertBrowseHistory(article)
            }
        }
    }
}