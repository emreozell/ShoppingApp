package com.example.shoppingapp.ui

import com.example.shoppingapp.models.Item


data class HomeViewState(
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val itemList: List<Item>? = null,
) {
}