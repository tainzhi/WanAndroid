package com.tainzhi.android.wanandroid.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 下午1:38
 * @description:
 **/


class SpaceItemDecoration(space: Int) : RecyclerView.ItemDecoration() {

    private val mSpace = space

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 0
        outRect.bottom = mSpace
    }

//    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        outRect?.top = 0
//        outRect?.bottom = mSpace
//    }
}
