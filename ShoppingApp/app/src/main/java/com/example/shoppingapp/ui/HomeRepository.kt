package com.example.shoppingapp.ui

import com.example.shoppingapp.domain.models.Response
import com.example.shoppingapp.models.Item
import com.example.shoppingapp.models.ItemResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getItems(): Flow<Response<List<Item>>>
}