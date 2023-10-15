package com.example.shoppingapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsOrder(
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "amount")
    val amount: Int? = null
)