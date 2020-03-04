package com.tainzhi.android.wanandroid.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.wanandroid.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.bean.Navigation
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.repository.NavigationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NavigationViewModel(private val navigationRepository: NavigationRepository,
                          private val historyDao: HistoryDao,
                          private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {
    private val _navigationList: MutableLiveData<List<Navigation>> = MutableLiveData()
    val navigationList: LiveData<List<Navigation>>
        get() = _navigationList

    fun getNavigation() {
        launch {
            val result = navigationRepository.getNavigation()
            if (result is Result.Success) emitData(result.data)
        }

    }

    fun insertBrowseHistory(article: Article) {
        launch() {
            historyDao.insertBrowseHistory(article)
        }
    }

    private suspend fun emitData(data: List<Navigation>) {
        withContext(Dispatchers.Main) {
            _navigationList.value = data
        }
    }
}