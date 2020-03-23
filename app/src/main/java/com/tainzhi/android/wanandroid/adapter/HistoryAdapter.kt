package com.tainzhi.android.wanandroid.adapter

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.databinding.ItemHistoryBinding

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/23 上午7:38
 * @description:
 **/

class HistoryAdapter(val callback: ((BrowseHistory) -> Unit)?) : PagedListAdapter<BrowseHistory, HistoryAdapter.HistoryViewHolder>(diffCallback) {
    
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<BrowseHistory>() {
            override fun areItemsTheSame(oldItem: BrowseHistory, newItem: BrowseHistory): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
            
            override fun areContentsTheSame(oldItem: BrowseHistory, newItem: BrowseHistory): Boolean {
                return oldItem.hashCode() == newItem.hashCode() &&
                        oldItem.article == newItem.article
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
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
        return HistoryViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.browseHistory = item
        holder.foreground = holder.binding.itemHistoryForeground
        holder.binding.run {
            setVariable(BR.browseHistory, item)
            executePendingBindings()
        }
    }
    
    class HistoryViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var foreground: View? = null
        var browseHistory: BrowseHistory? = null
    }
    
}

class RecyclerItemTouchHelper(
        dragDirs: Int, swipDirs: Int,
        private val mListener: RecyclerItemTouchHelperListener) : ItemTouchHelper.SimpleCallback(dragDirs, swipDirs) {
    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }
    
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }
    
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mListener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }
    
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            val foreground: View = (viewHolder as HistoryAdapter.HistoryViewHolder).foreground!!
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foreground)
        }
    }
    
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foreground: View = (viewHolder as HistoryAdapter.HistoryViewHolder).foreground!!
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foreground)
    }
    
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val foreground: View = (viewHolder as HistoryAdapter.HistoryViewHolder).foreground!!
        ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
    }
    
    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val foreground: View = (viewHolder as HistoryAdapter.HistoryViewHolder).foreground!!
        ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
    }
    
    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int)
    }
    
}
