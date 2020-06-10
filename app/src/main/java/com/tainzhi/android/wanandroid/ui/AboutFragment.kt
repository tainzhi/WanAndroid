package com.tainzhi.android.wanandroid.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.tainzhi.android.wanandroid.BuildConfig
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.common.base.ui.BaseFragment
import com.tainzhi.android.wanandroid.bean.UpdateInfo
import com.tainzhi.android.common.util.*
import com.tainzhi.android.wanandroid.WanApp
import com.tainzhi.android.wanandroid.util.GITHUB_PAGE
import com.tainzhi.android.wanandroid.util.ISSUE_URL
import com.tainzhi.android.wanandroid.util.UpdateUtils
import com.tainzhi.android.wanandroid.view.MeDialog
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.model.Notice
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_about

    override fun initView() {
        toolbar.setTitle(R.string.about)
        toolbar.setNavigationOnClickListener { onBack() }

        requireActivity().onBackPressedDispatcher.addCallback { onBack() }
    }

    override fun initData() {

        checkUpdateTv.setOnClickListener { checkUpdate() }
        licenseTv.setOnClickListener { showOwnLicense() }
        sourceTv.setOnClickListener { activity?.openBrowser(GITHUB_PAGE) }
        feedbackTv.setOnClickListener { showFeedBackMenu() }
        thirdLibTv.setOnClickListener { showLicenseDialog() }
        developerTv.setOnClickListener { showMe() }

        versionNameTv.text = BuildConfig.VERSION_NAME
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
        val feedbackMenu = context?.let { PopupMenu(it, feedbackTv, Gravity.END) }
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
        findNavController().navigateUp()
    }

    private fun showMe() {
        MeDialog.newInstance().show(childFragmentManager, getString(R.string.me))
    }

    /**
     * 检查是否有新版本可以下载
     */
    private fun checkUpdate() {
        val currVersionCode = AppInfoUtils.getVersionCode(WanApp.CONTEXT)
        val updateInfo = MemoryCache.instance?.get(MemoryCache.KEY_UPDATE_INFO) as? UpdateInfo
        if (updateInfo == null || currVersionCode >= updateInfo.versionCode) {
            AlertDialog.Builder(activity)
                    .setMessage(R.string.check_update_result)
                    .create()
                    .show()
        } else {
            UpdateUtils.newInstance().updateApp(activity as Context, updateInfo)
        }
    }
}