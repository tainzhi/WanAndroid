package com.tainzhi.android.wanandroid.ui.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.SystemParent
import com.tainzhi.android.wanandroid.repository.CollectRepository
import com.tainzhi.android.wanandroid.repository.SystemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/28 上午7:00
 * @description:
 **/

class SystemViewModel(
        private val systemRepository: SystemRepository,
        private val collectRepository: CollectRepository
) : BaseViewModel() {
    private val _systemParentList: MutableLiveData<BaseUiModel<List<SystemParent>>> = MutableLiveData()
    val uiState: LiveData<BaseUiModel<List<SystemParent>>>
        get() = _systemParentList

    fun getSystemTypes() {
        viewModelScope.launch(Dispatchers.Main) {
            emitArticleUiState(showLoading = true)
            val result = withContext(Dispatchers.IO) { systemRepository.getSystemTypes() }

            if (result is Result.Success)
                emitArticleUiState(showLoading = false, showSuccess = result.data)
            else if (result is Result.Error)
                emitArticleUiState(showLoading = false, showError = result.exception.message)
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                if (boolean) collectRepository.collectArticle(articleId)
                else collectRepository.unCollectArticle(articleId)
            }
        }
    }

    private fun emitArticleUiState(
            showLoading: Boolean = false,
            showError: String? = null,
            showSuccess: List<SystemParent>? = null
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _systemParentList.value = uiModel
    }
}