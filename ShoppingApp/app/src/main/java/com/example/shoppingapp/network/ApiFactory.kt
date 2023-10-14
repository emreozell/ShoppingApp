package com.example.shoppingapp.network

import com.example.shoppingapp.models.ItemResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiFactory {
    @GET("list")
    fun getItems(): Call<List<ItemResponse>>
}