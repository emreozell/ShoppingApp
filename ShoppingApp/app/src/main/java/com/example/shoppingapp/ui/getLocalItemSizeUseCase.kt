package com.example.shoppingapp.ui

import javax.inject.Inject

class getLocalItemSizeUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend  fun getAllItems()= homeRepositoryImpl.getLocalItemsSize()
}