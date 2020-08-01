package com.vjet.slambook.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.vjet.slambook.database.Item
import com.vjet.slambook.database.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel @ViewModelInject constructor(private val repo: ItemRepository) : ViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    val itemsLiveData = Pager(PagingConfig(pageSize = 1, initialLoadSize = 1)) {
        repo.getItems()
    }.liveData.cachedIn(viewModelScope)

    fun addItem(item: Item) =
        viewModelScope.launch(Dispatchers.IO) {
            repo.addItem(item)
        }

    fun updateItem(item: Item) =
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateItem(item)
            _item.postValue(item)
        }

    fun deleteItem(item: Item) =
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteItem(item)
        }

    fun setItem(item: Item) {
        _item.value = item
    }

}