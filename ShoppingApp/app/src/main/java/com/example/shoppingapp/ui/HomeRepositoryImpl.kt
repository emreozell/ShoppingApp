package com.example.shoppingapp.ui

import com.example.shoppingapp.domain.models.Response
import com.example.shoppingapp.models.Item
import com.example.shoppingapp.models.ItemEntity
import com.example.shoppingapp.models.ItemMapper
import com.example.shoppingapp.models.ItemResponse
import com.example.shoppingapp.network.ApiFactory
import com.example.shoppingapp.room.CartDAO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory,
    private val cartDAO: CartDAO
) : HomeRepository {
    override suspend fun getItems(): Flow<Response<List<Item>>> {
        return callbackFlow {
            val call = apiFactory.getItems()
            trySend(Response.Loading)
            call.enqueue(object : Callback<List<ItemResponse>> {
                override fun onResponse(
                    call: Call<List<ItemResponse>>,
                    response: retrofit2.Response<List<ItemResponse>>
                ) {
                    val itemResponses = response.body() ?: emptyList()
                    trySend(Response.Success(ItemMapper().mapToItemList(itemResponses)))
                }

                override fun onFailure(call: Call<List<ItemResponse>>, t: Throwable) {
                    trySend(
                        Response.Error(
                            _code = t.hashCode().toString(),
                            _message = t.localizedMessage
                        )
                    )
                }

            })
            awaitClose()
        }
    }

    override suspend fun insertDataBaseItem(item: Item) {
        if (item.totalOrder == 0) {
            cartDAO.delete(
                ItemEntity(
                    currency = item.currency,
                    id = item.id!!,
                    imageUrl = item.imageUrl,
                    name = item.name,
                    price = item.price,
                    stock = item.stock,
                    totalOrder = 1
                )
            )

        } else {
            val existingItem = cartDAO.getItemById(item.id!!)

            if (existingItem != null) {
                existingItem.totalOrder = item.totalOrder
                cartDAO.update(existingItem)
            } else {
                cartDAO.insert(
                    ItemEntity(
                        currency = item.currency,
                        id = item.id,
                        imageUrl = item.imageUrl,
                        name = item.name,
                        price = item.price,
                        stock = item.stock,
                        totalOrder = item.totalOrder
                    )
                )
            }
        }

    }

    override suspend fun getLocalItemsSize():Flow<Int> = cartDAO.getTotalCount()

}