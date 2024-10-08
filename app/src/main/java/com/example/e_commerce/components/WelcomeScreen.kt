package com.example.e_commerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val image: Painter = painterResource(id = R.drawable.shopping_splash) // Replace with your actual image resource
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp) // Adjust padding as needed
            ) {
                Text(
                    text = "Welcome to",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6D3D),
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        fontStyle = FontStyle.Italic
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "EZYDEALS",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6D3D),
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = 0.5.em
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Adjust padding as needed
            ) {

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { navController.navigate("signup") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D3D)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "LET'S BEGIN!",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
