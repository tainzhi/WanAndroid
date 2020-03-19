package com.tainzhi.android.wanandroid.bean

import java.io.Serializable

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/10 上午10:32
 * @description:
 **/
data class UpdateInfo(
        val versionCode: Int, // 173
        val description: String, // update
        val url: String, // https://gitee.com/qinmen/GithubServer/raw/master/WanAndroidQWanAndroid_v0.4.13_release.apk
        val url_backup: String, // https://github.com/tainzhi/WanAndroid/releases/download/v0.4.14/QWanAndroid_v0.4.13_release.apk
        val time: String, // 2020-03-10 08:18:47
        val apkName: String // QWanAndroid_v0.4.13_release.apk
) : Serializable
