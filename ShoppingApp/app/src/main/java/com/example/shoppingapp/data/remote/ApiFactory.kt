package com.example.shoppingapp.data.remote

import com.example.shoppingapp.domain.model.Posts
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiFactory {
    @GET("list")
    fun getItems(): Call<List<ItemResponse>>

    @POST("checkout")
    fun sendProducts(@Body posts: Posts): Call<SendResponse>
}
