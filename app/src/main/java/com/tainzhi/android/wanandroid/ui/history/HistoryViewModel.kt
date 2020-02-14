package com.tainzhi.android.wanandroid.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.db.HistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
        private val historyDao: HistoryDao
) : BaseViewModel() {
    private val _uiState = MutableLiveData<HistoryUiModel>()

    val uiState = _uiState

    fun getBrowseHistory(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                emitHistoryUIState(showLoading = true, isRefresh = isRefresh)
            }
            val browseHistory = historyDao.getBrowseHistory()

            withContext(Dispatchers.Main) {
                emitHistoryUIState(showLoading = false, showSuccesses = browseHistory, isRefresh = isRefresh)
            }
        }
    }

    fun refresh() {
        getBrowseHistory(isRefresh = true)
    }

    private fun emitHistoryUIState(
            showLoading: Boolean = false,
            showSuccesses: List<BrowseHistory>? = null,
            isRefresh: Boolean = false
    ) {
        val historyUiModel = HistoryUiModel(showLoading, showSuccesses, isRefresh)
        _uiState.value = historyUiModel
    }
}

data class HistoryUiModel(
        val showLoading: Boolean,
        val showSuccesses: List<BrowseHistory>?,
//        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean // 刷新
)
