package com.tainzhi.android.wanandroid.adapter

import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.tainzhi.android.wanandroid.bean.SystemParent

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午11:39
 * @description:
 **/

class SystemAdapter<T : ViewDataBinding>(layoutResId: Int, val br: Int) :
        BaseQuickAdapter<SystemParent,
                BaseDataBindingHolder<T>>(layoutResId) {

    override fun convert(holder: BaseDataBindingHolder<T>, item: SystemParent) {
        holder.dataBinding?.apply {
            setVariable(br, item)
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