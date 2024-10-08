package com.example.e_commerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.e_commerce.R

@Composable
fun PaymentConfirmationScreen(navController: NavHostController) {
    Scaffold(
        topBar = { AppTopAppBar(title = "Payment Confirmation", onAccountClick = { navController.navigate("profile") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Success Icon
//            Image(
//                painter = painterResource(id = R.drawable.ic_success),
//                contentDescription = "Payment Success",
//                modifier = Modifier.size(120.dp)
//            )
            Spacer(modifier = Modifier.height(16.dp))

            // Success Message
            Text(
                text = "Payment Successful!",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)  // Green color
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Thank You Message
            Text(
                text = "Thank you for your purchase.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Order Summary
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Dummy Order Details
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Order Number: #123456",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total Amount: $299.99",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Estimated Delivery: 3-5 Business Days",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Continue Shopping Button
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Continue Shopping",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}