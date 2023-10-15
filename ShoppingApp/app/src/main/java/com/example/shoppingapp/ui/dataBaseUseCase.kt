package com.example.shoppingapp.ui

import com.example.shoppingapp.domain.models.Response
import com.example.shoppingapp.models.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class dataBaseUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend operator fun invoke(item:Item)= homeRepositoryImpl.insertDataBaseItem(item)
}