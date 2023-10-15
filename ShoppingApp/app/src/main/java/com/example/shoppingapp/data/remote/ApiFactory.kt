package com.example.shoppingapp.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface ApiFactory {
    @GET("list")
    fun getItems(): Call<List<ItemResponse>>
}