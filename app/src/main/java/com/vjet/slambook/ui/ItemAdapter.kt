package com.vjet.slambook.ui

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.vjet.slambook.database.Item

class ItemAdapter(private val action: (Item, View) -> Unit) : PagingDataAdapter<Item, ItemHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(parent)

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bindView(item, action)
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }
}