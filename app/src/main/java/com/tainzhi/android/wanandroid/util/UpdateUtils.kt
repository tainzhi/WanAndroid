package com.tainzhi.android.wanandroid.util

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.bean.UpdateInfo
import java.util.*

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/10 下午1:02
 * @description:
 **/

class UpdateUtils private constructor() {
    private var ignoreCode by Preference(KEY_IGNORE_VERSION_CODE, 0)
    private var ignoreLastTime by Preference(KEY_IGNORE_LAST_TIME, 0L)
    private var ignoreDuration by Preference(KEY_IGNORE_DURATION, 0L)
    private var lastCheckTime by Preference(KEY_LAST_CHECK_TIME, 0L)

    fun ignore(versionCode: Int) {
        ignoreCode = versionCode
        ignoreLastTime = System.currentTimeMillis()
    }

    fun shouldUpdate(versionCode: Int): Boolean {
        if (!isNewest(versionCode)) {
            return false
        }
        if (versionCode > ignoreCode) {
            return true
        }
        val currTime = System.currentTimeMillis()
        return currTime - ignoreLastTime > ignoreDuration
    }

    fun isNewest(versionCode: Int): Boolean {
        val currCode: Int = AppInfoUtils.getVersionCode()
        return versionCode > currCode
    }

    fun isTodayChecked(): Boolean {
        val last: Long = lastCheckTime
        val curr = System.currentTimeMillis()
        lastCheckTime = curr
        val lastCalendar = Calendar.getInstance()
        lastCalendar.timeInMillis = last
        val currCalendar = Calendar.getInstance()
        currCalendar.timeInMillis = curr
        return lastCalendar.get(Calendar.YEAR) == currCalendar.get(Calendar.YEAR) &&
                lastCalendar.get(Calendar.DAY_OF_YEAR) == currCalendar.get(Calendar.DAY_OF_YEAR)

    }

    fun updateApp(context: Context, updateInfo: UpdateInfo) {
        val configuration = UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                .setDialogImage(R.drawable.update_dialog_bg)
                //设置按钮的颜色
                .setDialogButtonColor(ContextCompat.getColor(context, R.color.color_primary))
                //设置对话框强制更新时进度条和文字的颜色
                .setDialogProgressBarColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(ContextCompat.getColor(context, R.color.color_on_primary))
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置是否提示后台下载toast
                .setShowBgdToast(false)
                //设置强制更新
                .setForcedUpgrade(false)
        //设置对话框按钮的点击监听
        // .setButtonClickListener(this) //设置下载过程的监听
        // .setOnDownloadListener(this)

        val manager = DownloadManager.getInstance(context)
        manager.setApkName(updateInfo.apkName)
                .setApkUrl(updateInfo.url)
                .setApkDescription(updateInfo.description)
                .setApkVersionCode(updateInfo.versionCode)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setShowNewerToast(true)
                .setConfiguration(configuration)
                // .setApkVersionName(updateInfo.versionName)
                // .setApkSize()
                // .setApkMD5 // ("DC501F04BBAA458C9DC33008EFED5E7F")
                .download()
    }

    companion object {
        private const val KEY_IGNORE_VERSION_CODE = "KEY_IGNORE_VERSION_CODE"
        private const val KEY_IGNORE_LAST_TIME = "KEY_IGNORE_LAST_TIME"
        private const val KEY_IGNORE_DURATION = "KEY_IGNORE_DURATION"
        private const val KEY_LAST_CHECK_TIME = "KEY_LAST_CHECK_TIME"

        fun newInstance(): UpdateUtils {
            return UpdateUtils()
        }
    }
}
