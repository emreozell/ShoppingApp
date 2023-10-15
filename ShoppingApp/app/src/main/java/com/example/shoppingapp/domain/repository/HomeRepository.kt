package com.example.shoppingapp.domain.repository

import com.example.shoppingapp.core.Response
import com.example.shoppingapp.domain.model.Item
import com.example.shoppingapp.domain.model.Posts
import com.example.shoppingapp.data.remote.SendResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getItems(): Flow<Response<List<Item>>>
    suspend fun insertDataBaseItem(item: Item)
    suspend fun getLocalItemsSize():Flow<Int?>

    suspend fun getLocalItems():Flow<List<Item>>
    suspend fun getTotalPrice():Flow<Double?>

    suspend fun postOrders(posts: Posts):Flow<Response<SendResponse>>

    suspend fun deleteAllItems()
}