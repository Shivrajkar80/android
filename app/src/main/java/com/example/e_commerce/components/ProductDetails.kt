package com.example.e_commerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce.network.Product
import com.example.e_commerce.viewmodel.CartViewModel

@Composable
fun ProductDetails(navController: NavHostController, product: Product?, cartViewModel: CartViewModel) {
    if (product == null) {
        Text(text = "Product not found", style = MaterialTheme.typography.bodyLarge)
        return
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go Back",
                modifier = Modifier.size(24.dp)
            )
            Text(text = "Go Back", modifier = Modifier.padding(start = 8.dp))
        }

        Image(
            painter = rememberAsyncImagePainter(model = product.image),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = product.title,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Red,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { cartViewModel.addToCart(product) }) {
                Text(text = "Add to Cart")
            }
            Button(
                onClick = { /* Handle Buy Now action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Buy Now")
            }
        }
    }
}
