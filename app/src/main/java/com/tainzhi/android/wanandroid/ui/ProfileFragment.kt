package com.tainzhi.android.wanandroid.ui

import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.tainzhi.android.wanandroid.BuildConfig
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BaseFragment
import com.tainzhi.android.wanandroid.bean.User
import com.tainzhi.android.wanandroid.util.GITHUB_PAGE
import com.tainzhi.android.wanandroid.util.ISSUE_URL
import com.tainzhi.android.wanandroid.util.Preference
import com.tainzhi.android.wanandroid.util.openBrowser
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.model.Notice
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    override fun getLayoutResId() = R.layout.fragment_profile

    override fun initView() {
        titleTv.text = getString(R.string.me)
        versionName.text = BuildConfig.VERSION_NAME
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun initData() {

        license.setOnClickListener { showOwnLicense() }
        source.setOnClickListener { activity?.openBrowser(GITHUB_PAGE) }
        feedback.setOnClickListener { showFeedBackMenu() }
        thirdLib.setOnClickListener { showLicenseDialog() }
        developer.setOnClickListener { showMe() }
        loginLayout.setOnClickListener {
            if (!isLogin) Navigation.findNavController(loginLayout)
                    .navigate(R.id.action_homeFragment_to_loginActivity)
        }
        collect.setOnClickListener {
            if (isLogin) Navigation.findNavController(loginLayout)
                    .navigate(R.id.action_homeFragment_to_collectFragment)
        }
    }

    private fun refreshData() {
        if (isLogin) {
            val user = Gson().fromJson<User>(userJson, User::class.java)
            Glide.with(icon).load(user.icon).error(R.drawable.ic_dynamic_user).into(icon)
            loginTv.text = user.username
            collect.visibility = View.VISIBLE
        } else {
            loginTv.text = "登录/注册"
            collect.visibility = View.GONE
        }
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
        val feedbackMenu = context?.let { PopupMenu(it, feedback, Gravity.RIGHT) }
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

    private fun showMe() {
        context?.let {
            MaterialDialog(it).show {
                customView(R.layout.dialog_me)
            }
        }
    }
}

