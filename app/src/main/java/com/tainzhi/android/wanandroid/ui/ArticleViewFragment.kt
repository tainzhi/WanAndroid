package com.tainzhi.android.wanandroid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.ArticleList
import com.tainzhi.android.wanandroid.bean.Banner
import com.tainzhi.android.wanandroid.repository.HomeRepository

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/24 下午6:31
 * @description:
 **/

class ArticleViewFragment(
        private val homeRepository: HomeRepository
) : BaseViewModel() {
    sealed class ArticleType {
        object Home: ArticleType()
    }

    private val _uiState = MutableLiveData<ArticleUiModel>()

    val uiState: LiveData<ArticleUiModel>
        get() = _uiState

    private var currentPage = 0

    val banners: LiveData<List<Banner>> = liveData {
         kotlin.runCatching {
             val data = homeRepository.getBanners()
             if (data is Result.Success)
         }
    }

    data class ArticleUiModel(
            val showLoading: Boolean,
            val showError: String?,
            val showSuccess: ArticleList?,
            val showEnd: Boolean, // 加载更多
            val isRefresh: Boolean, // 刷新
            val needLogin: Boolean? = null
    )
}