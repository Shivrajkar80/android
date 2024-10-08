package com.example.e_commerce.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun MyOrderScreen(navController: NavHostController, userSessionViewModel: UserSessionViewModel = viewModel()) {
    val user by userSessionViewModel.user.observeAsState()
    val orders = user?.orders ?: emptyList()

    Scaffold(
        topBar = { AppTopAppBar(title = "My Orders", onAccountClick = { navController.navigate("profile") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (orders.isEmpty()) {
                Text("No orders found", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(orders) { order ->
                        OrderItem(order = order)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text("Order ID: ${order.orderId}", style = MaterialTheme.typography.bodyLarge)
        Text("Order Date: ${order.orderDate}", style = MaterialTheme.typography.bodySmall)
        Text("Total Amount: $${order.totalAmount}", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        order.items.forEach { item ->
            Text("Product: ${item.productName}, Quantity: ${item.quantity}, Price: $${item.price}", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
