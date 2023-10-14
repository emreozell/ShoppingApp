package com.example.shoppingapp.models


import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("stock")
    val stock: Int?
)