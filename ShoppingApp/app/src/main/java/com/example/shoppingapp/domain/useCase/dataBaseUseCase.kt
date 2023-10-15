package com.example.shoppingapp.domain.useCase

import com.example.shoppingapp.data.repo.HomeRepositoryImpl
import com.example.shoppingapp.domain.model.Item
import javax.inject.Inject

class dataBaseUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend operator fun invoke(item: Item)= homeRepositoryImpl.insertDataBaseItem(item)
}