package com.tainzhi.android.wanandroid.ui.login

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentRegisterBinding
import com.tainzhi.android.wanandroid.util.toast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.title_layout.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RegisterFragment : BaseVMFragment<LoginViewModel>(useBinding = true) {

    override fun getLayoutResId() = R.layout.fragment_register

    override fun initVM(): LoginViewModel = activity!!.getViewModel()

    override fun initView() {
        toolbar.setTitle(R.string.register)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBack() }

    }

    override fun initData() {
        (mBinding as FragmentRegisterBinding).viewModel = viewModel
    }

    override fun startObserve() {
        viewModel.apply {
            registerUser.observe(viewLifecycleOwner, Observer {
                it?.run {
                    viewModel.register()
                }
            })

            uiState.observe(viewLifecycleOwner, Observer {
                if (it.showProgress) {
                    dismissKeyboard(userNameEt.windowToken)
                }

                it.showSuccess?.let {
                    onBackToMain()
                }

                it.showError?.let { err ->
                    activity?.toast(err)
                }
            })
        }
    }

    private fun onBack() {
        findNavController().popBackStack()
    }

    private fun onBackToMain() {
        findNavController().popBackStack(R.id.mainFragment, false)
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
