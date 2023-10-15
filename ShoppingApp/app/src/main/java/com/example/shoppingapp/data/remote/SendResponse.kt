package com.example.shoppingapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendResponse(
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "orderID")
    val orderID: String? = null
)