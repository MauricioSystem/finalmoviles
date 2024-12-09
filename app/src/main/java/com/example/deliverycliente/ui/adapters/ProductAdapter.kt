package com.example.deliverycliente.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverycliente.data.models.Product
import com.example.deliverycliente.databinding.ItemProductBinding
import com.example.deliverycliente.CartManager
import com.squareup.picasso.Picasso

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productDescription.text = product.description
            binding.productPrice.text = "$${product.price}"

            // Usar Picasso para cargar la imagen
            Picasso.get()
                .load(product.image) // La URL de la imagen
                .placeholder(android.R.drawable.ic_menu_gallery) // Imagen de carga
                .error(android.R.drawable.ic_menu_report_image) // Imagen de error
                .into(binding.productImage)

            binding.addToCartButton.setOnClickListener {
                CartManager.addProduct(product)
                Toast.makeText(
                    binding.root.context,
                    "${product.name} agregado al carrito",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}
