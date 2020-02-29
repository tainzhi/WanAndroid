package com.tainzhi.android.wanandroid

import com.tainzhi.android.wanandroid.db.DateConverters
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/1 上午12:03
 * @description:
 **/

class ConverterTest {
    private val date = Date()
    private val dateLong = date.time.toLong()

    @Test
    fun dateToLong() {
        assertEquals(dateLong, DateConverters().dateToTimestamp(date))
    }

    @Test
    fun longToDate() {
        assertEquals(date, DateConverters().timestampToDate(dateLong))
    }
}