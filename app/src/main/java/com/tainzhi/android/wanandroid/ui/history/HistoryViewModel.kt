package com.tainzhi.android.wanandroid.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.db.HistoryBrowseBean
import com.tainzhi.android.wanandroid.db.HistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
        private val historyDao: HistoryDao
) : BaseViewModel() {
    private val _uiState = MutableLiveData<HistoryUiModel>()

    val uiState = _uiState

    fun getBrowseHistory() {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                emitHistoryUIState(showLoading = true)
            }
            val browseHistory = historyDao.getBrowseHistory()

            withContext(Dispatchers.Main) {
                emitHistoryUIState(showLoading = false, showSuccess = browseHistory)
            }
        }
    }

    fun refresh() {
        getBrowseHistory()
    }

    private fun emitHistoryUIState(
            showLoading: Boolean = false,
            showSuccess: List<HistoryBrowseBean>? = null,
            isRefresh: Boolean = false
    ) {
        val historyUiModel = HistoryUiModel(showLoading, showSuccess, isRefresh)
        _uiState.value = historyUiModel
    }
}

data class HistoryUiModel(
        val showLoading: Boolean,
        val showSuccess: List<HistoryBrowseBean>?,
//        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean // 刷新
)
