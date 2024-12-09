package com.example.deliverycliente.data.models

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val restaurant_id: Int,
    val quantity: Int
)


