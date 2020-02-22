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
    private var showStar = true

    fun showStar(showStar: Boolean) {
        this.showStar = showStar
    }

    override fun convert(helper: BindViewHolder, item: Article) {
        super.convert(helper, item)
        helper.addOnClickListener(R.id.articleStar)
        helper.setVisible(R.id.articleStar, showStar)
        if (item.collect) {
            helper.getView<ImageView>(R.id.articleStar).setImageResource(R.drawable.ic_favourite_check_24)
        } else {
            helper.getView<ImageView>(R.id.articleStar).setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }
}