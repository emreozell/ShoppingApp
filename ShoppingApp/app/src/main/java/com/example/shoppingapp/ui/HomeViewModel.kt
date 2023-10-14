package com.example.shoppingapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.models.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getItemsUseCase: getItemsUseCase):ViewModel() {

    fun getItems(){
        viewModelScope.launch {
                getItemsUseCase()
                    .collectLatest { response ->
                        when (response.status) {
                            ResponseStatus.LOADING -> {
                                Log.e("qqqqqq","loading")
                            }

                            ResponseStatus.SUCCESS -> {
                                Log.e("qqqqqq","success")
                                Log.e("qqqqqq",response.data.toString())
                            }

                            else -> {
                                Log.e("qqqqqq",response.message.toString())
                            }
                        }
                    }
        }

    }

}