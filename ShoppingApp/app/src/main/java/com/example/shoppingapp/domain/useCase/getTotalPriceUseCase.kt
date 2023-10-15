package com.example.shoppingapp.domain.useCase

import com.example.shoppingapp.data.repo.HomeRepositoryImpl
import javax.inject.Inject


class getTotalPriceUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend fun getTotalPrice()= homeRepositoryImpl.getTotalPrice()
}