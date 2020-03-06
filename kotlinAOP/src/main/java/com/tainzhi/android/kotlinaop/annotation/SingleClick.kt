package com.tainzhi.android.kotlinaop.annotation

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/6 下午3:08
 * @description:
 **/

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class SingleClick(val timeInterval: Long = 600)