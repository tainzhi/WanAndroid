package com.tainzhi.android.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.android.wanandroid.R

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/4 下午2:04
 * @description:
 **/

class SplashAdapter(private val imageSrcIdList: List<Int>) : RecyclerView.Adapter<SplashAdapter
.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemBg: ImageView = view.findViewById(R.id.splashItemBg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_splash, parent, false))

    override fun getItemCount() = Integer.MAX_VALUE

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBg.setImageResource(imageSrcIdList[position % imageSrcIdList.size])
    }
}