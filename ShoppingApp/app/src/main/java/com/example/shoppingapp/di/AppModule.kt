package com.example.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.example.shoppingapp.room.CartDAO
import com.example.shoppingapp.room.CartDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app : Context
    ) = Room.databaseBuilder(
        app,
        CartDataBase::class.java,
        "CartDatabase"
    ).build()

    @Singleton
    @Provides
    fun provideProductsDao(database : CartDataBase) = database.productsDao()
}