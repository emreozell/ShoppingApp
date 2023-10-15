package com.example.shoppingapp.domain.model

import com.example.shoppingapp.data.remote.PostsOrder
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Posts(
    @Json(name = "products")
    val products : List<PostsOrder>
)