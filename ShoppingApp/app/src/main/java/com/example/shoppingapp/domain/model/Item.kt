package com.example.shoppingapp.domain.model



data class Item(
    val currency: String?,
    val id: String?,
    val imageUrl: String?,
    val name: String?,
    val price: Double?,
    val stock: Int?,
    var totalOrder:Int=0,
)