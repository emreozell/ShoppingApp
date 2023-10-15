package com.example.shoppingapp.core.mapper

import com.example.shoppingapp.domain.model.Item
import com.example.shoppingapp.data.local.ItemEntity
import com.example.shoppingapp.data.remote.ItemResponse

class ItemMapper {
    fun mapToItem(itemResponse: ItemResponse): Item {
        return Item(
            currency = itemResponse.currency,
            id = itemResponse.id,
            imageUrl = itemResponse.imageUrl,
            name = itemResponse.name,
            price = itemResponse.price,
            stock = itemResponse.stock
        )
    }

    fun mapToItemList(itemResponses: List<ItemResponse>): List<Item> {
        return itemResponses.map { mapToItem(it) }
    }

    fun mapItemEntitiesToItems(itemEntities: List<ItemEntity>): List<Item> {
        return itemEntities.map { itemEntity ->
            Item(
                currency = itemEntity.currency,
                id = itemEntity.id,
                imageUrl = itemEntity.imageUrl,
                name = itemEntity.name,
                price = itemEntity.price,
                stock = itemEntity.stock,
                totalOrder = itemEntity.totalOrder ?: 0
            )
        }
    }

}
