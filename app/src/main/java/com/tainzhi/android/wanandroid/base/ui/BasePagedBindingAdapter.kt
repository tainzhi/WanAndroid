package com.tainzhi.android.wanandroid.base.ui

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午11:25
 * @description:
 **/

abstract class BasePagedBindingAdapter<T, V : ViewDataBinding>(
        diffCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, BasePagedBindingAdapter.PagedBindViewHolder<V>>(diffCallback) {
    
    override fun onBindViewHolder(holder: PagedBindViewHolder<V>, position: Int) {
        
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }
    
    abstract fun bind(binding: V, item: T?)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedBindViewHolder<V> {
        val binding = createBinding(parent)
        return PagedBindViewHolder(binding)
    }
    
    abstract fun createBinding(parent: ViewGroup): V
    
    class PagedBindViewHolder<out VT : ViewDataBinding> constructor(val binding: VT) : RecyclerView.ViewHolder(binding.root)
}