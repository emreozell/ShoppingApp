package com.example.shoppingapp.domain.useCase

import com.example.shoppingapp.data.repo.HomeRepositoryImpl
import com.example.shoppingapp.domain.model.Posts
import javax.inject.Inject

class postOrdersUseCase @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) {
    suspend fun postOrders(posts: Posts)= homeRepositoryImpl.postOrders(posts)
}