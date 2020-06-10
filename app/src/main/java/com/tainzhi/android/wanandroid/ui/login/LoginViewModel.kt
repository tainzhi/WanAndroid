package com.tainzhi.android.wanandroid.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.common.CoroutinesDispatcherProvider
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.common.base.Result
import com.tainzhi.android.common.base.ui.BaseViewModel
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.repository.LoginRepository

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/24 下午1:29
 * @description:
 **/

class LoginViewModel(
        val repository: LoginRepository,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {

    private val mIsLogin: MutableLiveData<Boolean> = WanApp.preferenceRepository.mIsLogin
    private val mUser: MutableLiveData<User> = WanApp.preferenceRepository.mUser

    val isLogin: LiveData<Boolean> = mIsLogin
    val user: LiveData<User> = mUser

    val userName = ObservableField<String>("")
    val passWord = ObservableField<String>("")
    val rePassWord = ObservableField<String>("")
    val errorHint = ObservableField<String>("")


    private val _uiState = MutableLiveData<LoginUiModel>()

    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    val registerUser: MutableLiveData<User> = MutableLiveData()

    private fun isInputValid(input: String) = input.isNotBlank()

    private fun loginDataChanged() {
            emitUiState(enableLoginButton = isInputValid(userName.get() ?: "") &&
                    isInputValid(passWord.get() ?: ""))
    }

    fun login() {
        launch {
            if (userName.get().isNullOrBlank() || passWord.get().isNullOrBlank()) {
                emitUiState(enableLoginButton = false)
                return@launch
            }

            emitUiState(showProgress = true)

            val result = repository.login(userName.get() ?: "", passWord
                    .get() ?: "")

            checkResult(result, {
                emitUiState(showProgress = false, showSuccess = it, enableLoginButton = true)
            }, {
                emitUiState(showProgress = false, showError = it, enableLoginButton = true)
            })
        }
    }

    fun register() {
        launch {
            if (userName.get().isNullOrBlank() || passWord.get().isNullOrBlank()) return@launch

            emitUiState(showProgress = true)

            val result = repository.register(userName.get() ?: "",
                    passWord.get() ?: "")
            if (result is Result.Success) {
                emitUiState(showProgress = false, showSuccess = result.data, enableLoginButton = true)
            } else if (result is Result.Error) {
                emitUiState(showProgress = false, showError = result.exception.message, enableLoginButton = true)
            }
        }
    }

    private inline fun <T : Any> checkResult(result: Result<T>, success: (T) -> Unit, error:
    (String?) -> Unit) {
        if (result is Result.Success) {
            success(result.data)
        } else if (result is Result.Error) {
            error(result.exception.message)
        }
    }

    val verifyInput: () -> Unit = { loginDataChanged() }

    val verifyRegisterInput: () -> Unit = {
        if (passWord.get().equals(rePassWord.get())) {
            errorHint.set("")
        } else {
            errorHint.set("两次输入密码不一致")

        }
            emitUiState(enableLoginButton = isInputValid(userName.get() ?: "") &&
                    isInputValid(passWord.get() ?: "") &&
                    isInputValid(rePassWord.get() ?: "") &&
                    errorHint.get() == "")
    }

    private fun emitUiState(
            showProgress: Boolean = false,
            showError: String? = null,
            showSuccess: User? = null,
            enableLoginButton: Boolean = false,
            needLogin: Boolean = false
    ) {
            val uiModel = LoginUiModel(showProgress, showError, showSuccess, enableLoginButton, needLogin)
            // 登录或者注册成功
            if (showSuccess != null) {
                updateUser(showSuccess)
            }
            _uiState.postValue(uiModel)
    }

    private fun updateUser(user: User) {
        mIsLogin.postValue(true)
        mUser.postValue(user)
    }

}

data class LoginUiModel(
        val showProgress: Boolean,
        val showError: String?,
        val showSuccess: User?,
        val enableLoginButton: Boolean,
        val needLogin: Boolean
)