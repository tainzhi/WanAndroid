package com.tainzhi.android.wanandroid.view

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/4 上午11:48
 * @description:
 **/

class ScrollLinearLayoutManager(private val context: Context) :
        LinearLayoutManager(context) {
    // 数字越大，自动滚动速度越慢
    var scrollSpeed = 5f
        set(value) {
            field = (context.resources.displayMetrics.density * 0.5)
                    .toFloat() + value
        }

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView?.context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return scrollSpeed / displayMetrics.density
            }

            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@ScrollLinearLayoutManager.computeScrollVectorForPosition(targetPosition)
            }
        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}