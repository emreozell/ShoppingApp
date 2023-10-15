package com.example.shoppingapp.domain.useCase

import com.example.shoppingapp.data.repo.HomeRepositoryImpl
import javax.inject.Inject

class getLocalItemSizeUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend  fun getItemsSize()= homeRepositoryImpl.getLocalItemsSize()
}