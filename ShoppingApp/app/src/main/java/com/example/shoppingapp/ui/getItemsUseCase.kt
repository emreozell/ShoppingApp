package com.example.shoppingapp.ui

import com.example.shoppingapp.domain.models.Response
import com.example.shoppingapp.models.Item
import com.example.shoppingapp.models.ItemResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getItemsUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend operator fun invoke(): Flow<Response<List<Item>>> =
        homeRepositoryImpl.getItems()
}