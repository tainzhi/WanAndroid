package com.tainzhi.android.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.databinding.ItemArticleBinding

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午11:39
 * @description:
 **/

class HomeArticleAdapter : BaseQuickAdapter<Article, BaseDataBindingHolder<ItemArticleBinding>> {
    constructor() : super(R.layout.item_article)
    
    override fun convert(holder: BaseDataBindingHolder<ItemArticleBinding>, item: Article) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()
        }
    }
    // private var showCollect = true
    //
    // fun showCollect(showCollect: Boolean) {
    //     this.showCollect = showCollect
    // }
    //
    // override fun convert(helper: BaseBindAdapter.BindViewHolder, item: Article) {
    //     super.convert(helper, item)
    //     helper.addOnClickListener(R.id.collectIv)
    //     helper.setVisible(R.id.collectIv, showCollect)
    //     if (item.collect) {
    //         helper.getView<ImageView>(R.id.collectIv).setImageResource(R.drawable.ic_collect_check_24)
    //     } else {
    //         helper.getView<ImageView>(R.id.collectIv).setImageResource(R.drawable.ic_collect_unchecked_24)
    //     }
    // }
}