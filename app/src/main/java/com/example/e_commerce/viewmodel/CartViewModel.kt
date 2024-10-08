package com.example.e_commerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.network.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CartItem(
    val product: Product,
    val quantity: Int
)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(product: Product) {
        val existingItem = _cartItems.value.find { it.product.id == product.id }
        if (existingItem != null) {
            _cartItems.value = _cartItems.value.map {
                if (it.product.id == product.id) it.copy(quantity = it.quantity + 1)
                else it
            }
        } else {
            _cartItems.value = _cartItems.value + CartItem(product, 1)
        }
    }

    fun removeFromCart(product: Product) {
        val existingItem = _cartItems.value.find { it.product.id == product.id }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                _cartItems.value = _cartItems.value.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity - 1)
                    else it
                }
            } else {
                _cartItems.value = _cartItems.value.filterNot { it.product.id == product.id }
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}