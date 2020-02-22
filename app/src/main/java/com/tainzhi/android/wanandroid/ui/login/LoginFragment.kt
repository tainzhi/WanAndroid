package com.tainzhi.android.wanandroid.ui.login

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentLoginBinding
import com.tainzhi.android.wanandroid.util.toast
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginFragment : BaseVMFragment<LoginViewModel>(useBinding = true) {

    override fun getLayoutResId() = R.layout.fragment_login

    override fun initVM(): LoginViewModel = activity!!.getViewModel()

    override fun initView() {
        toolbar.setTitle(R.string.login)
        toolbar.setNavigationOnClickListener { onBack() }

        registerTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun initData() {
        (mBinding as FragmentLoginBinding).viewModel = viewModel
    }

    override fun startObserve() {
        viewModel.apply {

            uiState.observe(viewLifecycleOwner, Observer {

            if (it.showProgress) {
                    dismissKeyboard(userNameEt.windowToken)
                }

                it.showSuccess?.let {
                    activity?.toast(R.string.login_success)
                    onBack()
                }

                it.showError?.let { err ->
                    activity?.toast(err)
                }
            })
        }
    }


    private fun onBack() {
        dismissKeyboard(toolbar.windowToken)
        findNavController().popBackStack(R.id.mainFragment, false)
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}