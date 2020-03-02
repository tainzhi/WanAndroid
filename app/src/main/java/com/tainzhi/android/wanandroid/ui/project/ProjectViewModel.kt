package com.tainzhi.android.wanandroid.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.wanandroid.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.SystemParent
import com.tainzhi.android.wanandroid.repository.ProjectRepository
import kotlinx.coroutines.withContext

class ProjectViewModel(
        private val repository: ProjectRepository,
        private val dispatcher: CoroutinesDispatcherProvider
) : BaseViewModel() {

    private val _mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()
    val systemData: LiveData<List<SystemParent>>
        get() = _mSystemParentList

    fun getProjectTypeList() {
        launch {
            val result = withContext(dispatcher.computation) { repository.getProjectTypeList() }
            if (result is Result.Success) _mSystemParentList.value = result.data
        }
    }

    fun getBlogType() {
        launch {
            val result = withContext(dispatcher.computation) { repository.getBlog() }
            if (result is Result.Success) _mSystemParentList.value = result.data
        }
    }
}