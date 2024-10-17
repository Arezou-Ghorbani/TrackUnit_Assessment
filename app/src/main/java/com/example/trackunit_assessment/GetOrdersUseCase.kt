package com.example.trackunit_assessment

import com.example.trackunit_assessment.model.Order
import com.example.trackunit_assessment.utils.readRawTextFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class GetOrdersUseCase {

    suspend operator fun invoke(): List<Order> {
        val dataStr = readRawTextFile(R.raw.data)
        val dataType = TypeToken.getParameterized(List::class.java, Order::class.java).type
//        fake delay to be like getting data from server ;)
        delay(1200)
        return Gson().fromJson(dataStr, dataType)
    }

}