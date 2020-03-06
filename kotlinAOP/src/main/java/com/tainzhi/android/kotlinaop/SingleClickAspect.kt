package com.tainzhi.android.kotlinaop

import android.util.Log
import com.tainzhi.android.kotlinaop.annotation.SingleClick
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.util.*

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/6 下午3:10
 * @description:
 **/

@Aspect
internal class SingleClickAspect {
    @Pointcut("execution(@com.tainzhi.android.kotlinaop.annotation.SingleClick * *(..))")
    fun methodAnnotated() {

    }

    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val timeValue: SingleClick = methodSignature.method.getAnnotation(SingleClick::class.java)!!
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > timeValue.timeInterval) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "currentTime:$currentTime")
            }
            lastClickTime = currentTime
            joinPoint.proceed()
        }
        lastClickTime = currentTime
    }

    companion object {
        const val TAG = "kotlinAOP/SingleClick"
        var lastClickTime = 0L
    }
}
