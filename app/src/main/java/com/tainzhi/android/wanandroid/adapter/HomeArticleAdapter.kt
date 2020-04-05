package com.tainzhi.android.wanandroid.adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
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

class HomeArticleAdapter<T: ViewDataBinding>(layoutResId: Int, val br: Int) :
        BaseQuickAdapter<Article,
        BaseDataBindingHolder<T>>(layoutResId), LoadMoreModule {

    override fun convert(holder: BaseDataBindingHolder<T>, item: Article) {
        holder.dataBinding?.apply {
            setVariable(br, item)
            executePendingBindings()
        }
//        holder.run {
//            setVisible(R.id.collectIv, showCollect)
//            addChildClickViewIds(R.id.collectIv)
//            if (item.collect) {
//                holder.getView<ImageView>(R.id.collectIv).setImageResource(R.drawable.ic_collect_check_24)
//            } else {
//                holder.getView<ImageView>(R.id.collectIv).setImageResource(R.drawable.ic_collect_unchecked_24)
//            }
//        }
    }
     private var showCollect = true

     fun showCollect(showCollect: Boolean) {
         this.showCollect = showCollect
     }

}