package com.example.shoppingapp.domain.useCase

import com.example.shoppingapp.data.repo.HomeRepositoryImpl
import javax.inject.Inject

class getLocalItemsUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend fun getAllItems()= homeRepositoryImpl.getLocalItems()
}