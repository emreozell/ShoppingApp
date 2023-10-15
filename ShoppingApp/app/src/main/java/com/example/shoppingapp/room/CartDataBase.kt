package com.example.shoppingapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingapp.models.ItemEntity

@Database(entities = [ItemEntity::class], version = 1)
abstract class CartDataBase : RoomDatabase() {
    abstract fun productsDao(): CartDAO
}