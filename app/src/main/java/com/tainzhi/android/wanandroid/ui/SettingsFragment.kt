package com.tainzhi.android.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_settings,
                container,
                false
        )
        binding.lifecycleOwner = viewLifecycleOwner

//        initView()
//        initData()

        return binding.root
    }
//    private fun initView() {
//        toolbar.setTitle(R.string.settings)
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
//        toolbar.setNavigationOnClickListener { onBack() }
//
//        requireActivity().onBackPressedDispatcher.addCallback { onBack() }
//    }
//
//    private fun initData() {
//
//        binding.changeThemeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->  }
//        licenseTv.setOnClickListener { showOwnLicense() }
//        sourceTv.setOnClickListener { activity?.openBrowser(GITHUB_PAGE) }
//        feedbackTv.setOnClickListener { showFeedBackMenu() }
//        thirdLibTv.setOnClickListener { showLicenseDialog() }
//        developerTv.setOnClickListener { showMe() }
//    }
//
//    private fun showOwnLicense() {
//        val notice = Notice(getString(R.string.app_name), GITHUB_PAGE, "", ApacheSoftwareLicense20())
//        LicensesDialog.Builder(activity)
//                .setNotices(notice)
//                .build()
//                .show()
//    }
//
//    private fun showLicenseDialog() {
//        LicensesDialog.Builder(activity)
//                .setNotices(R.raw.licenses)
//                .build()
//                .show()
//    }
//
//    private fun showFeedBackMenu() {
//        val feedbackMenu = context?.let { PopupMenu(it, feedbackTv, Gravity.END) }
//        feedbackMenu?.menuInflater?.inflate(R.menu.feedback_menu, feedbackMenu.menu)
//        feedbackMenu?.setOnMenuItemClickListener { item: MenuItem ->
//            when (item.itemId) {
//                R.id.menu_issue -> {
//                    activity?.openBrowser(ISSUE_URL)
//                    true
//                }
//                R.id.menu_email -> {
//                    val uri = Uri.parse(getString(R.string.sendto))
//                    val intent = Intent(Intent.ACTION_SENDTO, uri)
//                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic))
////                    intent.putExtra(Intent.EXTRA_TEXT,
////                            getString(R.string.device_model) + Build.MODEL + "\n"
////                                    + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
////                                    + getString(R.string.version))version
//                    startActivity(intent)
//                    true
//                }
//                else -> {
//                    true
//                }
//            }
//        }
//        feedbackMenu?.show()
//    }
//
//    private fun onBack() {
//        findNavController().popBackStack(R.id.mainFragment, false)
//    }
//
//    private fun showMe() {
//        context?.let {
//            MaterialDialog(it).show {
//                customView(R.layout.dialog_me)
//            }
//        }
//    }
}

