package com.example.deliverycliente.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverycliente.CartManager
import com.example.deliverycliente.data.api.ApiClient
import com.example.deliverycliente.data.models.OrderDetail
import com.example.deliverycliente.data.models.OrderRequest
import com.example.deliverycliente.data.models.Product
import com.example.deliverycliente.databinding.ActivityCartBinding
import com.example.deliverycliente.ui.adapters.CartAdapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        val cartProducts = CartManager.getCartItems()
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = CartAdapter(cartProducts)

        // Configurar el botón "Hacer Pedido"
        binding.btnMakeOrder.setOnClickListener {
            makeOrder(cartProducts)
        }
    }

    private fun makeOrder(products: List<Product>) {
        if (products.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = ApiClient.getApiService()

        // Crear lista de detalles
        val details: List<OrderDetail> = products.map {
            OrderDetail(
                product_id = it.id,
                qty = it.quantity,
                price = it.price
            )
        }

        // Crear el cuerpo del POST
        val orderRequest = OrderRequest(
            restaurant_id = products.first().restaurant_id,
            total = products.sumOf { it.price * it.quantity },
            address = "Av banzer 100",
            latitude = "-17.769170204594246",
            longitude = "-63.18297131152056",
            details = details
        )

        // Verificar datos en el Log
        Log.d("OrderRequest", orderRequest.toString())

        // Llamada al POST
        apiService.makeOrder(orderRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CartActivity, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show()
                    CartManager.clearCart() // Limpiar carrito después del pedido
                    binding.cartRecyclerView.adapter = CartAdapter(emptyList()) // Actualizar vista del carrito
                    finish() // Cerrar la actividad del carrito
                } else {
                    Toast.makeText(this@CartActivity, "Error al realizar el pedido", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CartActivity", "Error al realizar el pedido: ${t.message}", t)
                Toast.makeText(this@CartActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
