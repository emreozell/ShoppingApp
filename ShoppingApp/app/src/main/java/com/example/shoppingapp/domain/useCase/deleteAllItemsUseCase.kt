package com.example.shoppingapp.domain.useCase

import com.example.shoppingapp.data.repo.HomeRepositoryImpl
import javax.inject.Inject

class deleteAllItemsUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend fun deleteAll()=homeRepositoryImpl.deleteAllItems()
}