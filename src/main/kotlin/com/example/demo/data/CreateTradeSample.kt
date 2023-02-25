package com.example.demo.data

data class CreateTradeSample(

    var orderId : String,

    var exchangeOrderId : String,

    var price: Double,

    var quantity: Double,

    var exchangeOrderTime: String,

    var createdBy: String
)
