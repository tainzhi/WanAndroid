package com.tainzhi.android.wanandroid.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tainzhi.android.wanandroid.CoroutinesDispathcherProvider
import com.tainzhi.android.wanandroid.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.repository.LoginRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/24 下午1:29
 * @description:
 **/
 
class LoginViewModel(val repository: LoginRepository, val provider:
CoroutinesDispathcherProvider): BaseViewModel() {
    val userName = ObservableField<String>("")
    val passWord = ObservableField<String>("")

    private val _uiState = MutableLiveData<LoginUiModel>()

    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    val registerUser: MutableLiveData<User> = MutableLiveData()

    private fun isInputValid(userName:String, passWord: String) = userName.isNotBlank() &&
            passWord.isNotBlank()

    fun loginDataChanged() {
        emitUiState(enableLoginButton = isInputValid(userName.get() ?: "",
                passWord.get() ?: ""))
    }

    fun login() {
        viewModelScope.launch(provider.computation) {
            if (userName.get().isNullOrBlank() || passWord.get().isNullOrBlank()) {
                emitUiState(enableLoginButton = false)
                return@launch
            }

            withContext(provider.main) { showLoading() }

            val result = repository.login(userName.get() ?: "", passWord.get() ?: "")

            withContext(provider.main) {
                checkResult(result, {
                    emitUiState(showSuccess = it, enableLoginButton = true)
                }, {
                    emitUiState(showError = it, enableLoginButton = true)
                })
            }
        }
    }

    fun register() {
        viewModelScope.launch(provider.computation) {
            if (userName.get().isNullOrBlank() || passWord.get().isNullOrBlank())  return@launch

            withContext(provider.main) { showLoading()}

            val result = repository.register(userName.get() ?: "", passWord.get() ?: "")
            withContext(provider.main) {
                if (result is Result.Success) {
                    emitUiState(showSuccess = result.data, enableLoginButton = true)
                } else if (result is Result.Error) {
                    emitUiState(showError = result.exception.message, enableLoginButton = true)
                }
            }
        }
    }

    private inline fun <T: Any> checkResult(result: Result<T>, success: (T) -> Unit, error:
    (String?) -> Unit) {
        if (result is Result.Success) {
            success(result.data)
        } else if (result is Result.Error){
            error(result.exception.message)
        }
    }

    val verifyInput: (String) -> Unit = {loginDataChanged()}

    private fun showLoading(){
        emitUiState(true)
    }


    private fun emitUiState(
            showProgress: Boolean = false,
            showError: String? = null,
            showSuccess: User? = null,
            enableLoginButton: Boolean = false,
            needLogin: Boolean = false
    ) {
        val uiModel = LoginUiModel(showProgress, showError, showSuccess, enableLoginButton, needLogin)
        _uiState.value = uiModel
    }

}


data class LoginUiModel(
        val showProgress: Boolean,
        val showError: String?,
        val showSuccess: User?,
        val enableLoginButton: Boolean,
        val needLogin: Boolean
)