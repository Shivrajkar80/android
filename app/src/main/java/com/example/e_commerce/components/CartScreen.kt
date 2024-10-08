package com.example.e_commerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce.viewmodel.CartItem
import com.example.e_commerce.viewmodel.CartViewModel

@Composable
fun CartScreen(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }

    Scaffold(
        topBar = { AppTopAppBar(title = "EZYDEALS", onAccountClick = { navController.navigate("profile") }) },
        bottomBar = { AppBottomNavigationBar(navController, selectedTab = 2) { /* Handle tab selection */ } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Empty cart",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Gray,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(cartItems) { cartItem ->
                        CartItemRow(
                            cartItem = cartItem,
                            onAddClick = { cartViewModel.addToCart(cartItem.product) },
                            onRemoveClick = { cartViewModel.removeFromCart(cartItem.product) }
                        )
                    }
                }

                // Checkout Section
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Price: $${"%.2f".format(totalPrice)}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate("payment/${"%.2f".format(totalPrice)}") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Checkout",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, onAddClick: () -> Unit, onRemoveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray.copy(alpha = 0.2f))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image and Product Details
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = cartItem.product.image),
                contentDescription = cartItem.product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.product.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: $${cartItem.product.price}",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
        // Quantity and Action Buttons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onRemoveClick,
                modifier = Modifier
                    .size(56.dp)
                    .padding(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.2f),
                    contentColor = Color.Red
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            ) {
                Text(
                    text = "-",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 24.sp
                    )
                )
            }
            Text(
                text = cartItem.quantity.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Button(
                onClick = onAddClick,
                modifier = Modifier
                    .size(56.dp)
                    .padding(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green.copy(alpha = 0.2f),
                    contentColor = Color.Green
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = Color.Green,
                        fontSize = 24.sp
                    )
                )
            }
        }
    }
}
