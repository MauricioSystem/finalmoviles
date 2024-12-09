package com.example.deliverycliente.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverycliente.data.api.ApiClient
import com.example.deliverycliente.data.models.Restaurant
import com.example.deliverycliente.databinding.FragmentRestaurantsBinding
import com.example.deliverycliente.ui.CartActivity
import com.example.deliverycliente.ui.adapters.RestaurantAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantsFragment : Fragment() {

    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuración del RecyclerView
        binding.restaurantsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Cargar los restaurantes desde la API
        loadRestaurants()

        // Botón flotante para ir al carrito
        binding.fabCart.setOnClickListener {
            val intent = Intent(requireContext(), CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadRestaurants() {
        val apiService = ApiClient.getApiService()
        apiService.getRestaurants().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(
                call: Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                if (response.isSuccessful) {
                    val restaurants = response.body() ?: emptyList()
                    if (restaurants.isNotEmpty()) {
                        binding.restaurantsRecyclerView.adapter = RestaurantAdapter(restaurants)
                    } else {
                        Toast.makeText(context, "No hay restaurantes disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Error al cargar los restaurantes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                Toast.makeText(context, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
