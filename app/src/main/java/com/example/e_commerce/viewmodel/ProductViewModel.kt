package com.example.e_commerce.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.network.Product
import com.example.e_commerce.network.RetrofitInstance
import com.example.e_commerce.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ProductViewModel(private val context: Context) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> get() = _categories




    init {
        fetchProducts()
        fetchCategories()
    }

    private fun fetchProducts(category: String? = null) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            // Handle no internet connection
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val productsList = if (category == null) {
                    RetrofitInstance.api.getProducts()
                } else {
                    RetrofitInstance.api.getProductsByCategory(category)
                }
                _products.value = productsList
            } catch (e: IOException) {
                // Handle network error
                Toast.makeText(context, "Failed to fetch products. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCategories() {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            // Handle no internet connection
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val categoriesList = RetrofitInstance.api.getCategories()
                _categories.value = categoriesList
            } catch (e: IOException) {
                // Handle network error
                Toast.makeText(context, "Failed to fetch categories. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onCategorySelected(category: String) {
        fetchProducts(category)
    }
    fun getProductById(productId: String?): Product? {
        val id = productId?.toIntOrNull() // Safely convert String? to Int?
        return _products.value.find { it.id == id }
    }

}


