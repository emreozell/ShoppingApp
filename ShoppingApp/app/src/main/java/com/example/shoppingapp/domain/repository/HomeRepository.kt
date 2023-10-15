package com.example.shoppingapp.domain.repository

import com.example.shoppingapp.core.Response
import com.example.shoppingapp.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getItems(): Flow<Response<List<Item>>>
    suspend fun insertDataBaseItem(item: Item)
    suspend fun getLocalItemsSize():Flow<Int?>

    suspend fun getLocalItems():Flow<List<Item>>
    suspend fun deleteAllItems()
}