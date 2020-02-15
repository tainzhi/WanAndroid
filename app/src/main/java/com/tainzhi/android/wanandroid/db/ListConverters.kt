package com.tainzhi.android.wanandroid.db

import androidx.room.TypeConverter

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/15 上午9:41
 * @description:
 **/

object ListConverters {

    @TypeConverter
    fun stringToStringList(data: String?): List<String>? {
        return data?.let {
            it.split(",").filterNotNull()
        }
    }

    @TypeConverter
    @JvmStatic
    fun stringListToString(ints: List<String>?): String? {
        return ints?.joinToString(",")
    }

}