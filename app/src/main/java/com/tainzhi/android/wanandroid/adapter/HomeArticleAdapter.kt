package com.tainzhi.android.wanandroid.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import com.chad.library.BR
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.databinding.ItemArticleBinding
import com.tainzhi.android.wanandroid.di.databaseModule

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午11:39
 * @description:
 **/

class HomeArticleAdapter :
        BaseQuickAdapter<Article,
        BaseDataBindingHolder<ItemArticleBinding>>(R.layout.item_article), LoadMoreModule {


    override fun convert(holder: BaseDataBindingHolder<ItemArticleBinding>, item: Article) {
        holder.dataBinding?.apply {
            // setVariable(br, item)
            // setVariable(BR.collect, ObservableBoolean(item.collect))
            article = item
            collect = ObservableBoolean(item.collect)
            addChildClickViewIds(R.id.collectIv)
            executePendingBindings()
        }
        holder.setVisible(R.id.collectIv, showCollect)
    }
     private var showCollect = true

     fun showCollect(showCollect: Boolean) {
         this.showCollect = showCollect
     }
}
