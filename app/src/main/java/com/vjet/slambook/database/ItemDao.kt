package com.vjet.slambook.database

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * from items ORDER BY id ASC")
    fun getItems(): PagingSource<Int, Item>

    @Insert
    suspend fun addItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}