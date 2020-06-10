package com.tainzhi.android.wanandroid.ui.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.common.CoroutinesDispatcherProvider
import com.tainzhi.android.common.base.Result
import com.tainzhi.android.common.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.SystemParent
import com.tainzhi.android.wanandroid.repository.CollectRepository
import com.tainzhi.android.wanandroid.repository.SystemRepository
import kotlinx.coroutines.withContext

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/28 上午7:00
 * @description:
 **/

class SystemViewModel(
        private val systemRepository: SystemRepository,
        private val collectRepository: CollectRepository,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {
    private val _systemParentList: MutableLiveData<BaseUiModel<List<SystemParent>>> = MutableLiveData()
    val uiState: LiveData<BaseUiModel<List<SystemParent>>>
        get() = _systemParentList

    fun getSystemTypes() {
        launch {
            emitArticleUiState(showLoading = true)
            val result = systemRepository.getSystemTypes()

            if (result is Result.Success)
                emitArticleUiState(showLoading = false, showSuccess = result.data)
            else if (result is Result.Error)
                emitArticleUiState(showLoading = false, showError = result.exception.message)
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launch {
            if (boolean) collectRepository.collectArticle(articleId)
            else collectRepository.unCollectArticle(articleId)
        }
    }

    private suspend fun emitArticleUiState(
            showLoading: Boolean = false,
            showError: String? = null,
            showSuccess: List<SystemParent>? = null
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        withContext(dispatcherProvider.main) {
            _systemParentList.value = uiModel
        }
    }
}