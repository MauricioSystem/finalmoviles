package com.example.deliverycliente.data.models

data class OrderDetail(
    val product_id: Int,
    val qty: Int,
    val price: Double
)

data class OrderRequest(
    val restaurant_id: Int,
    val total: Double,
    val address: String,
    val latitude: String,
    val longitude: String,
    val details: List<OrderDetail>
)
