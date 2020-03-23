package com.tainzhi.android.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.base.ui.BasePagedBindingAdapter
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.databinding.ItemHistoryBinding

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/23 上午7:38
 * @description:
 **/

class HistoryAdapter(val callback: ((BrowseHistory) -> Unit)?) : BasePagedBindingAdapter<BrowseHistory, ItemHistoryBinding>(
        diffCallback = object : DiffUtil.ItemCallback<BrowseHistory>() {
            override fun areItemsTheSame(oldItem: BrowseHistory, newItem: BrowseHistory): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
            
            override fun areContentsTheSame(oldItem: BrowseHistory, newItem: BrowseHistory): Boolean {
                return oldItem.hashCode() == newItem.hashCode() &&
                        oldItem.article == newItem.article
            }
        }
) {
    override fun bind(binding: ItemHistoryBinding, item: BrowseHistory?) {
        binding.run {
            setVariable(BR.browseHistory, item)
        }
    }
    
    override fun createBinding(parent: ViewGroup): ItemHistoryBinding {
        val binding = DataBindingUtil
                .inflate<ItemHistoryBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.item_history,
                        parent,
                        false
                )
        binding.root.setOnClickListener {
            binding.browseHistory?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }
    
}