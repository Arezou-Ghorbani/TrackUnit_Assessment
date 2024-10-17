package com.example.trackunit_assessment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackunit_assessment.GetOrdersUseCase
import com.example.trackunit_assessment.StateOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val getOrdersUseCase = GetOrdersUseCase()

    private val _uiState = MutableStateFlow<StateOrder>(StateOrder.Idle)
    val uiState: StateFlow<StateOrder> = _uiState.asStateFlow()


    fun getOrderData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(StateOrder.Loading)
            _uiState.emit(StateOrder.Data(getOrdersUseCase()))
        }
    }

}