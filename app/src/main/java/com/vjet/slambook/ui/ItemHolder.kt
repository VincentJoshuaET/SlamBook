package com.vjet.slambook.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vjet.slambook.database.Item
import com.vjet.slambook.databinding.ItemBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ItemHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(
        ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private val formatter =
        DateTimeFormatter.ofPattern("MMMM d, yyyy").withZone(ZoneId.systemDefault())

    fun bindView(item: Item, action: (Item, View) -> Unit) =
        binding.run {
            root.setCardBackgroundColor(item.card)
            val birthday =
                Instant.ofEpochMilli(item.birthday).atZone(ZoneId.systemDefault()).toLocalDate()
            txtFullName.text = item.fullName
            txtNickname.text = item.nickname
            txtAddress.text = item.address
            txtMobile.text = item.mobile
            txtEmail.text = item.email
            txtGender.text = item.gender
            txtBirthday.text = birthday.format(formatter)
            txtAge.text = item.age.toString()
            itemView.setOnClickListener {
                action(item, itemView)
            }
        }

}