package com.tainzhi.android.wanandroid.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.toLiveData
import com.tainzhi.android.wanandroid.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.db.HistoryDao

class HistoryViewModel(
        private val historyDao: HistoryDao,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {
    
    private val _uiState = MutableLiveData<HistoryUiModel>()
    
    val uiState: LiveData<HistoryUiModel>
        get() = _uiState
    
    
    val allBrowseHistory =
            historyDao.getAllBrowseHistory().toLiveData(
                    Config(
                            pageSize = 10,
                            enablePlaceholders = true,
                            maxSize = 30
                    )
            )
    
    fun deleteBrowseHistory() {
        launch {
            // emitHistoryUIState(showLoading = true)
            historyDao.deleteBrowseHistory()
            // emitHistoryUIState(showLoading = false, isDelete = true)
        }
    }
    
    fun insertBrowseHistory(article: Article) {
        launch {
            historyDao.insertBrowseHistory(article)
        }
    }
}

data class HistoryUiModel(
        val showLoading: Boolean,
        val showSuccesses: List<BrowseHistory>?,
        //        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean, // 刷新,
        val showEnd: Boolean,
        val isDelete: Boolean
)