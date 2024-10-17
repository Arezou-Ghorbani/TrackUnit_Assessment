package com.example.trackunit_assessment.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name:String,
    @SerializedName("price")
    val price:Int,
)
