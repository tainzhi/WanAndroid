package com.tainzhi.android.wanandroid.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import com.tainzhi.android.wanandroid.WanApp

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/9 下午10:23
 * @description: AppInfo Utils
 **/

object AppInfoUtils {
    /**
     * 获取本地软件版本号code
     */
    fun getVersionCode(): Int {
        var localVersion = 0
        try {
            val packageInfo: PackageInfo = WanApp.CONTEXT.packageManager.getPackageInfo(
                    WanApp.CONTEXT.packageName, 0)
            localVersion = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return localVersion
    }

    /**
     * 获取本地软件版本号name
     */
    fun getVersionName(): String {
        var versionName = ""
        try {
            val packageInfo: PackageInfo = WanApp.CONTEXT.packageManager.getPackageInfo(
                    WanApp.CONTEXT.packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }

    /**
     * 判断应用是否已安装
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return getAppInfo(context, packageName) != null
    }

    fun getAppInfo(context: Context, packageName: String): ApplicationInfo? {
        return if (TextUtils.isEmpty(packageName)) {
            null
        } else try {
            context.packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}
