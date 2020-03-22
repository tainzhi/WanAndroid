package com.tainzhi.android.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.databinding.ItemHistoryBinding

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 上午11:25
 * @description:
 **/

class HistoryAdapter() : PagedListAdapter<BrowseHistory, HistoryAdapter.PagedBindViewHolder>(diffCallback) {
    
    override fun onBindViewHolder(holder: PagedBindViewHolder, position: Int) {
        holder.binding.run {
            // setVariable(_br, getItem(position))
            browseHistory = getItem(position)
            executePendingBindings()
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedBindViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_history,
                parent, false)
        return PagedBindViewHolder(binding)
    }
    
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BrowseHistory>() {
            override fun areItemsTheSame(oldItem: BrowseHistory, newItem: BrowseHistory): Boolean {
                return oldItem == newItem
            }
            
            override fun areContentsTheSame(oldItem: BrowseHistory, newItem: BrowseHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
    
    class PagedBindViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)
}