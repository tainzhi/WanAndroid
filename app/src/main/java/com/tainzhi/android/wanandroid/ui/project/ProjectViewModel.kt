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
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {

    private val _mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()
    val systemData: LiveData<List<SystemParent>>
        get() = _mSystemParentList

    fun getProjectTypeList() {
        launch {
            val result = repository.getProjectTypeList()
            if (result is Result.Success) emitData(result.data)
        }
    }

    fun getBlogType() {
        launch {
            val result = repository.getBlog()
            if (result is Result.Success) emitData(result.data)
        }
    }

    private suspend fun emitData(data: List<SystemParent>) {
        withContext(dispatcherProvider.main) {
            _mSystemParentList.value = data
        }
    }
}