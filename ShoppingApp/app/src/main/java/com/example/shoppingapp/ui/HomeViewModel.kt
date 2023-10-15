package com.example.shoppingapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.models.ResponseStatus
import com.example.shoppingapp.models.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemsUseCase: getItemsUseCase,
    private val dataBaseUseCase: dataBaseUseCase,
    private val getLocalItemSizeUseCase: getLocalItemSizeUseCase
) :
    ViewModel() {
    private val _localListSizeLiveData = MutableLiveData<Int>()
    val localListSİzeLiveData: LiveData<Int> = _localListSizeLiveData

    init {
        viewModelScope.launch {
            getLocalItemSizeUseCase.getAllItems().collect {
                _localListSizeLiveData.value = it
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

    fun updateDataBase(item: Item) {
        viewModelScope.launch {
            dataBaseUseCase(item)
        }
    }

}