package com.tainzhi.android.wanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.ArticleList
import com.tainzhi.android.wanandroid.bean.Hot
import com.tainzhi.android.wanandroid.db.HistoryDao
import com.tainzhi.android.wanandroid.db.HistorySearchBean
import com.tainzhi.android.wanandroid.repository.CollectRepository
import com.tainzhi.android.wanandroid.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val searchRepository: SearchRepository,
                      private val collectRepository: CollectRepository,
                      private val historyDao: HistoryDao
) : BaseViewModel() {

    private var currentPage = 0

    private val _uiState = MutableLiveData<SearchUiModel>()
    val uiState: LiveData<SearchUiModel>
        get() = _uiState


    fun searchHot(isRefresh: Boolean = false, key: String) {
        viewModelScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.Main) { emitArticleUiState(showLoading = true) }
            if (isRefresh) currentPage = 0

            val result = searchRepository.searchHot(currentPage, key)

            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    val articleList = result.data
                    if (articleList.offset >= articleList.total) {
                        emitArticleUiState(showLoading = false, showEnd = true)
                        return@withContext
                    }
                    currentPage++
                    emitArticleUiState(showLoading = false, showSuccess = articleList, isRefresh = isRefresh)

                } else if (result is Result.Error) {
                    emitArticleUiState(showLoading = false, showError = result.exception.message)
                }

            }

        }
    }

    fun getSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val searchHistory = historyDao.getSearchHistory()
            emitArticleUiState(showHot = true, showSearchHistory = searchHistory.)
        }
    }

    fun insertSearchHistory(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            historyDao.insertSearchKey(HistorySearchBean(0, key))
        }
    }


    fun getWebSites() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { searchRepository.getWebSites() }
            if (result is Result.Success) emitArticleUiState(showHot = true, showWebSites = result.data)
        }
    }

    fun getHotSearch() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { searchRepository.getHotSearch() }
            if (result is Result.Success) emitArticleUiState(showHot = true, showHotSearch = result.data)
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                if (boolean) collectRepository.collectArticle(articleId)
                else collectRepository.unCollectArticle(articleId)
            }
        }
    }

    private fun emitArticleUiState(
            showHot: Boolean = false,
            showLoading: Boolean = false,
            showError: String? = null,
            showSuccess: ArticleList? = null,
            showEnd: Boolean = false,
            isRefresh: Boolean = false,
            showWebSites: List<Hot>? = null,
            showHotSearch: List<Hot>? = null,
            showSearchHistory: List<String>? = null
    ) {
        val uiModel = SearchUiModel(showHot, showLoading, showError, showSuccess, showEnd,
                isRefresh, showWebSites, showHotSearch, showSearchHistory)
        _uiState.value = uiModel
    }


    data class SearchUiModel(
            val showHot: Boolean,
            val showLoading: Boolean,
            val showError: String?,
            val showSuccess: ArticleList?,
            val showEnd: Boolean, // 加载更多
            val isRefresh: Boolean, // 刷新
            val showWebSites: List<Hot>?,
            val showHotSearch: List<Hot>?,
            val showSearchHistory: List<String>?
    )

}
