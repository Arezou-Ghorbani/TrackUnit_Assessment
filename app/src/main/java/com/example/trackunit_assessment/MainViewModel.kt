package com.example.trackunit_assessment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val getOrdersUseCase = GetOrdersUseCase()

    private val _uiState = MutableStateFlow<StateOrder>(StateOrder.Idle)
    val uiState: StateFlow<StateOrder> = _uiState


    fun getOrderData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = StateOrder.Loading
            val orders = getOrdersUseCase()
            _uiState.value = StateOrder.Data(orders)
        }
    }

}