package com.vjet.slambook.database

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val dao: ItemDao) {
    fun getItems() = dao.getItems()
    suspend fun addItem(item: Item) = dao.addItem(item)
    suspend fun updateItem(item: Item) = dao.updateItem(item)
}