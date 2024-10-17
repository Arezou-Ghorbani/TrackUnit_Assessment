package com.example.trackunit_assessment.model

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("orderId")
    val orderId: Long,
    @SerializedName("customerId")
    val customerId: Long,
    @SerializedName("totalPrice")
    val totalPrice: Long,
    @SerializedName("list")
    val items: List<Product>
)