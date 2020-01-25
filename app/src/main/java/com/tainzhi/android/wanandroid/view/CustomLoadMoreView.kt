package com.tainzhi.android.wanandroid.view

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.tainzhi.android.wanandroid.R

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 下午1:49
 * @description:
 **/


class CustomLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.view_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }
}
