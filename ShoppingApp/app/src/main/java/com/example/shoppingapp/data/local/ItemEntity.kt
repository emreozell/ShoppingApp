package com.example.shoppingapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val currency: String?,
    val imageUrl: String?,
    val name: String?,
    val price: Double?,
    val stock: Int?,
    var totalOrder:Int?,
)