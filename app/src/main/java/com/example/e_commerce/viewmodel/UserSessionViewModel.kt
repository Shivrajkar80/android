package com.example.e_commerce.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

data class User(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val profilePictureUrl: String = "",
    val orders: List<Order> = listOf()
)

data class Order(
    val orderId: String = "",
    val orderDate: String = "",
    val items: List<OrderItem> = listOf(),
    val totalAmount: Double = 0.0
)

data class OrderItem(
    val productId: String = "",
    val productName: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0
)

class UserSessionViewModel : ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    init {
        fetchUserDetails()
        fetchUserOrders()
    }

    private fun fetchUserDetails() {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid

        userId?.let {
            firestore.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val user = document.toObject(User::class.java)
                        _user.value = user
                    }
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    private fun fetchUserOrders() {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid

        userId?.let {
            firestore.collection("orders")
                .whereEqualTo("userId", it)
                .get()
                .addOnSuccessListener { documents ->
                    val orders = documents.map { document ->
                        document.toObject(Order::class.java)
                    }
                    _user.value = _user.value?.copy(orders = orders)
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    fun addOrder(order: Order) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid

        userId?.let {
            firestore.collection("orders").add(order.copy(orderId = firestore.collection("orders").document().id))
                .addOnSuccessListener {
                    fetchUserOrders()
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    fun updateUserProfilePicture(url: String) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid

        userId?.let {
            firestore.collection("users").document(it)
                .update("profilePictureUrl", url)
                .addOnSuccessListener {
                    _user.value = _user.value?.copy(profilePictureUrl = url)
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    fun updateUserDetails(name: String, email: String, phoneNumber: String) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid

        userId?.let {
            firestore.collection("users").document(it)
                .update("name", name, "email", email, "phoneNumber", phoneNumber)
                .addOnSuccessListener {
                    _user.value = _user.value?.copy(name = name, email = email, phoneNumber = phoneNumber)
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signOut()
            onLogoutComplete()
        }
    }
}
