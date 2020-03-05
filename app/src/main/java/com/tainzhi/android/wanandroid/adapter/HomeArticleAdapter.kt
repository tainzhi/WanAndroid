package com.tainzhi.android.wanandroid.adapter

import android.widget.ImageView
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.bean.Article

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午11:39
 * @description:
 **/

class HomeArticleAdapter(layoutResId: Int = R.layout.item_article) : BaseBindAdapter<Article>
(layoutResId, BR.article) {
    private var showCollect = true

    fun showCollect(showCollect: Boolean) {
        this.showCollect = showCollect
    }

    override fun convert(helper: BindViewHolder, item: Article) {
        super.convert(helper, item)
        helper.addOnClickListener(R.id.collectIv)
        helper.setVisible(R.id.collectIv, showCollect)
        if (item.collect) {
            helper.getView<ImageView>(R.id.collectIv).setImageResource(R.drawable.ic_collect_check_24)
        } else {
            helper.getView<ImageView>(R.id.collectIv).setImageResource(R.drawable.ic_collect_unchecked_24)
        }
    }
}