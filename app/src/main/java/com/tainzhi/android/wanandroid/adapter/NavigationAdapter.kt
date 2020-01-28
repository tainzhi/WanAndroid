package com.tainzhi.android.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.bean.Article
import com.tainzhi.android.wanandroid.bean.Navigation
import com.tainzhi.android.wanandroid.ui.BrowserActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/28 下午1:19
 * @description:
 **/

class NavigationAdapter(layoutResId: Int = R.layout.item_navigation) :
        BaseQuickAdapter<Navigation, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: Navigation) {
        helper.setText(R.id.navigationName, item.name)
        helper.getView<TagFlowLayout>(R.id.navigationTagLayout).run {
            adapter = object : TagAdapter<Article>(item.articles) {
                override fun getCount() = item.articles.size

                override fun getView(parent: FlowLayout?, position: Int, t: Article?): View {
                    val tv = LayoutInflater.from(parent?.context)
                            .inflate(R.layout.tag, parent, false) as TextView
                    tv.text = t?.title
                    return tv
                }
            }

            setOnTagClickListener { view, position, _ ->
                androidx.navigation.Navigation.findNavController(view).navigate(R.id.action_tabFragment_to_browserActivity,
                        bundleOf(BrowserActivity.URL to item.articles[position].link))
                true
            }
        }
    }
}