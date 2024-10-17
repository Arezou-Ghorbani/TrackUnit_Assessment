package com.example.trackunit_assessment

import com.example.trackunit_assessment.model.Order

sealed class StateOrder {
    data object Idle : StateOrder()
    data object Loading : StateOrder()
    data class Data(val data : List<Order>) : StateOrder()
}
