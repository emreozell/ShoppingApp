package com.example.shoppingapp.ui

import com.example.shoppingapp.data.remote.SendResponse
import com.example.shoppingapp.domain.model.Item


data class HomeViewState(
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val itemList: List<Item>? = null,
    val totalPrice:Double?=null,
    val postResponse: SendResponse?=null,
    val localListSize:Int?=null
) {
}