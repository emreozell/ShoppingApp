package com.example.shoppingapp.models

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
}
