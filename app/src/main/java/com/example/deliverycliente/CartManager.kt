package com.example.deliverycliente

import com.example.deliverycliente.data.models.Product

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addProduct(product: Product) {
        cartItems.add(product)
    }

    fun getCartItems(): List<Product> {
        return cartItems
    }

    fun clearCart() {
        cartItems.clear()
    }
}
