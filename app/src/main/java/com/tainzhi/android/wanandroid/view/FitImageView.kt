package com.tainzhi.android.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.ceil

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/4 上午11:13
 * @description:
 **/

class FitImageView : AppCompatImageView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (drawable != null) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = ceil(width.toDouble() * drawable.intrinsicHeight / drawable
                    .intrinsicWidth).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}