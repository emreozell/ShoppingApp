package com.example.shoppingapp.data.repo

import com.example.shoppingapp.core.Response
import com.example.shoppingapp.domain.model.Item
import com.example.shoppingapp.data.local.ItemEntity
import com.example.shoppingapp.core.mapper.ItemMapper
import com.example.shoppingapp.data.remote.ItemResponse
import com.example.shoppingapp.data.remote.ApiFactory
import com.example.shoppingapp.data.local.CartDAO
import com.example.shoppingapp.domain.repository.HomeRepository
import com.example.shoppingapp.domain.model.Posts
import com.example.shoppingapp.data.remote.SendResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
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

    override suspend fun getLocalItemsSize():Flow<Int?> = cartDAO.getTotalCount()
    override suspend fun getLocalItems(): Flow<List<Item>> {
        return cartDAO.getAllItems().map { itemEntities ->
            ItemMapper().mapItemEntitiesToItems(itemEntities)
        }
    }

    override suspend fun getTotalPrice(): Flow<Double?> =cartDAO.getTotalPrice()
    override suspend fun postOrders(posts: Posts): Flow<Response<SendResponse>> {
        return callbackFlow {
            trySend(Response.Loading)
           val call= apiFactory.sendProducts(posts)
            call.enqueue(object : Callback<SendResponse> {
                override fun onResponse(
                    call: Call<SendResponse>,
                    response: retrofit2.Response<SendResponse>
                ) {
                    val itemResponses = response.body()
                    trySend(Response.Success(_data = itemResponses))
                }

                override fun onFailure(call: Call<SendResponse>, t: Throwable) {
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

    override suspend fun deleteAllItems() =cartDAO.deleteAll()

}