package com.tainzhi.android.wanandroid.ui

import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.tainzhi.android.wanandroid.BuildConfig
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseFragment
import com.tainzhi.android.wanandroid.util.GITHUB_PAGE
import com.tainzhi.android.wanandroid.util.ISSUE_URL
import com.tainzhi.android.wanandroid.util.openBrowser
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.model.Notice
import kotlinx.android.synthetic.main.fragment_other_info.*
import kotlinx.android.synthetic.main.title_layout.*

class OtherInfoFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_other_info

    override fun initView() {
        toolbar.setTitle(R.string.other_info)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBack() }

        requireActivity().onBackPressedDispatcher.addCallback { onBack() }

        versionName.text = BuildConfig.VERSION_NAME
    }

    override fun initData() {

        license.setOnClickListener { showOwnLicense() }
        source.setOnClickListener { activity?.openBrowser(GITHUB_PAGE) }
        feedback.setOnClickListener { showFeedBackMenu() }
        thirdLib.setOnClickListener { showLicenseDialog() }
        developer.setOnClickListener { showMe() }
    }

    private fun showOwnLicense() {
        val notice = Notice(getString(R.string.app_name), GITHUB_PAGE, "", ApacheSoftwareLicense20())
        LicensesDialog.Builder(activity)
                .setNotices(notice)
                .build()
                .show()
    }

    private fun showLicenseDialog() {
        LicensesDialog.Builder(activity)
                .setNotices(R.raw.licenses)
                .build()
                .show()
    }

    private fun showFeedBackMenu() {
        val feedbackMenu = context?.let { PopupMenu(it, feedback, Gravity.END) }
        feedbackMenu?.menuInflater?.inflate(R.menu.feedback_menu, feedbackMenu.menu)
        feedbackMenu?.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_issue -> {
                    activity?.openBrowser(ISSUE_URL)
                    true
                }
                R.id.menu_email -> {
                    val uri = Uri.parse(getString(R.string.sendto))
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic))
//                    intent.putExtra(Intent.EXTRA_TEXT,
//                            getString(R.string.device_model) + Build.MODEL + "\n"
//                                    + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
//                                    + getString(R.string.version))version
                    startActivity(intent)
                    true
                }
                else -> {
                    true
                }
            }
        }
        feedbackMenu?.show()
    }

    private fun onBack() {
        findNavController().popBackStack(R.id.mainFragment, false)
    }

    private fun showMe() {
        context?.let {
            MaterialDialog(it).show {
                customView(R.layout.dialog_me)
            }
        }
    }
}

