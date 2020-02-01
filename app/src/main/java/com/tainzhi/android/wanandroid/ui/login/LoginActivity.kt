package com.tainzhi.android.wanandroid.ui.login

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseVMActivity
import com.tainzhi.android.wanandroid.databinding.ActivityLoginBinding
import com.tainzhi.android.wanandroid.util.toast
import kotlinx.android.synthetic.main.title_layout.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginActivity : BaseVMActivity<LoginViewModel>(useBinding = true) {

    override fun getLayoutResId() = R.layout.activity_login

    override fun initVM(): LoginViewModel = getViewModel()

    override fun initView() {
        toolbar.setTitle(R.string.login)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun initData() {
    }

    override fun startObserve() {
        viewModel.apply {
            registerUser.observe(this@LoginActivity, Observer {
                it?.run {
                    viewModel.login()
                }
            })

            uiState.observe(this@LoginActivity, Observer {
                if (it.showProgress) showProgressDialog()

                it.showProgress?.let {
                    dismissProgressDialog()
                    finish()
                }

                it.showError?.let { err ->
                    dismissProgressDialog()
                    toast(err)
                }
            })
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}