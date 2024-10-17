package com.example.trackunit_assessment

import com.example.trackunit_assessment.model.Order
import com.example.trackunit_assessment.model.Product
import kotlinx.coroutines.delay

class GetOrdersUseCase {

    suspend operator fun invoke(): List<Order> {
//        val dataStr = readRawTextFile(R.raw.data)?.trimIndent()
//
//        Log.i("TAG", "invoke: " + dataStr)
//        val dataType = object : TypeToken<List<Order>>() {}.type
////        fake delay for get data from server ;)
//        delay(1200)
//        return Gson().fromJson(dataStr, dataType)

        delay(1200)
        return listOf(
            Order(
                orderId = 25,
                customerId = 456,
                totalPrice = 1744,
                items = listOf(
                    Product(id = 1, name = "Far Cry 5", price = 445),
                    Product(id = 2, name = "Beats Solo 3 Wireless", price = 1299)
                )
            ),
            Order(
                orderId = 42,
                customerId = 456,
                totalPrice = 3000,
                items = listOf(
                    Product(id = 4, name = "Commodore 64", price = 3000)
                )
            )
        )

    }

}