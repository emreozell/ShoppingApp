package com.example.shoppingapp.domain.useCase

import com.example.shoppingapp.core.Response
import com.example.shoppingapp.data.repo.HomeRepositoryImpl
import com.example.shoppingapp.domain.model.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getItemsUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend operator fun invoke(): Flow<Response<List<Item>>> =
        homeRepositoryImpl.getItems()
}