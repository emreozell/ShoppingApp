package com.example.shoppingapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.core.ResponseStatus
import com.example.shoppingapp.domain.model.Item
import com.example.shoppingapp.domain.model.Posts
import com.example.shoppingapp.domain.useCase.dataBaseUseCase
import com.example.shoppingapp.domain.useCase.deleteAllItemsUseCase
import com.example.shoppingapp.domain.useCase.getItemsUseCase
import com.example.shoppingapp.domain.useCase.getLocalItemSizeUseCase
import com.example.shoppingapp.domain.useCase.getLocalItemsUseCase
import com.example.shoppingapp.domain.useCase.getTotalPriceUseCase
import com.example.shoppingapp.domain.useCase.postOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getItemsUseCase: getItemsUseCase,
    private val dataBaseUseCase: dataBaseUseCase,
    private val getLocalItemSizeUseCase: getLocalItemSizeUseCase,
    private val getLocalItemsUseCase: getLocalItemsUseCase,
    private val deleteAllItemsUseCase: deleteAllItemsUseCase,
    private val getTotalPriceUseCase: getTotalPriceUseCase,
    private val postOrdersUseCase: postOrdersUseCase

) :
    ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items
    fun getItemSize() {
        viewModelScope.launch {
            getLocalItemSizeUseCase.getItemsSize().collect {
                if (it != null) {
                    _viewState.update { viewState ->
                        viewState.copy(
                            localListSize = it
                        )
                    }
                } else {
                    _viewState.update { viewState ->
                        viewState.copy(
                            localListSize = 0
                        )
                    }
                }
            }

        }
    }

    fun getLocalItems() {
        viewModelScope.launch {
            getLocalItemsUseCase.getAllItems().collect {
                _items.value = it
            }
        }
    }

    private val _viewState =
        MutableStateFlow(HomeViewState())
    val viewState = _viewState.asStateFlow()

    fun getItems() {
        viewModelScope.launch {
            getItemsUseCase()
                .collect { response ->
                    when (response.status) {
                        ResponseStatus.LOADING -> _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }

                        ResponseStatus.SUCCESS -> _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = null,
                                itemList = response.data
                            )
                        }

                        else -> {
                            _viewState.update { viewState ->
                                viewState.copy(
                                    isLoading = false,
                                    errorMessage = response.message
                                )
                            }
                        }
                    }
                }
        }
    }

    fun deleteAllItems() {
        viewModelScope.launch {
            deleteAllItemsUseCase.deleteAll()
        }
    }

    fun updateDataBase(item: Item) {
        viewModelScope.launch {
            dataBaseUseCase(item)
        }
    }

    fun getTotalPrice() {
        viewModelScope.launch {
            getTotalPriceUseCase.getTotalPrice().collect {
                _viewState.update { viewState ->
                    viewState.copy(
                        totalPrice = it
                    )
                }
            }
        }
    }

    fun postOrders(posts: Posts) {
        viewModelScope.launch {
            postOrdersUseCase.postOrders(posts).collect { response ->
                when (response.status) {
                    ResponseStatus.LOADING -> _viewState.update { viewState ->
                        viewState.copy(
                            isLoading = true,
                            errorMessage = null
                        )
                    }

                    ResponseStatus.SUCCESS -> _viewState.update { viewState ->
                        viewState.copy(
                            isLoading = false,
                            errorMessage = null,
                            postResponse = response.data
                        )
                    }

                    else -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = response.message
                            )
                        }
                    }
                }

            }
        }
    }

    fun resetPostResponse() {
        _viewState.update { viewState ->
            viewState.copy(
                postResponse = null
            )
        }
    }

}