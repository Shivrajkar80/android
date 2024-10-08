package com.example.e_commerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.e_commerce.R
import com.example.e_commerce.viewmodel.CartViewModel

@Composable
fun PaymentScreen(navController: NavHostController, totalPrice: Double, cartViewModel: CartViewModel = viewModel()) {
    var selectedPaymentMethod by remember { mutableStateOf("Credit Card") }

    Scaffold(
        topBar = { AppTopAppBar(title = "Payment", onAccountClick = { navController.navigate("profile") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Price: $${"%.2f".format(totalPrice)}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Select Payment Method",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Payment Options
            Column {
                PaymentOption(
                    option = "Credit Card",
                    selectedOption = selectedPaymentMethod,
                    onSelectOption = { selectedPaymentMethod = it },
                    logoResId = R.drawable.ic_credit_card
                )
                PaymentOption(
                    option = "PayPal",
                    selectedOption = selectedPaymentMethod,
                    onSelectOption = { selectedPaymentMethod = it },
                    logoResId = R.drawable.ic_paypal
                )
                PaymentOption(
                    option = "Google Pay",
                    selectedOption = selectedPaymentMethod,
                    onSelectOption = { selectedPaymentMethod = it },
                    logoResId = R.drawable.ic_google_pay
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    cartViewModel.clearCart()
                    navController.navigate("payment_confirmation")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Pay Now",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun PaymentOption(option: String, selectedOption: String, onSelectOption: (String) -> Unit, logoResId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onSelectOption(option) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (option == selectedOption),
            onClick = { onSelectOption(option) },
            colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = logoResId),
            contentDescription = "$option Logo",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = option,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}