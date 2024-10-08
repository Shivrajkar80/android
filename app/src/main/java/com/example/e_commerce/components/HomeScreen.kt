package com.example.e_commerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.e_commerce.R

@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { AppTopAppBar(title = "EZYDEALS", onAccountClick = { navController.navigate("profile") }) },
        bottomBar = { AppBottomNavigationBar(navController, selectedTab) { selectedTab = it } }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Banner()
                Spacer(modifier = Modifier.height(16.dp))
                CategoriesSection()
                Spacer(modifier = Modifier.height(16.dp))
                FeaturedProductsSection()
            }
        }
    }
}

@Composable
fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set the height of the banner
            .background(Color.Gray) // Optional: Set a background color in case the image doesn't load
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner), // Replace with your image resource
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxSize() // Make the image fill the entire box
                .background(Color.Gray), // Optional: Set a background color for the image
            contentScale = ContentScale.Crop // Crop the image to fill the container
        )
    }
}


@Composable
fun CategoriesSection() {
    Column {
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryItem("Women", R.drawable.womans_icon)
            CategoryItem("Men", R.drawable.man_icon)
            CategoryItem("Electronics", R.drawable.phone_icon)
            CategoryItem("Jewelry", R.drawable.new_jewellery)
        }
    }
}

@Composable
fun CategoryItem(name: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(80.dp)
                .background(Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name)
    }
}

@Composable
fun FeaturedProductsSection() {
    val products = listOf(
        R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4,
        R.drawable.image_5, R.drawable.image_6, R.drawable.image_7, R.drawable.image_8,
        R.drawable.image_9, R.drawable.image_10
    )

    Column {
        Text(
            text = "Featured Products",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        products.chunked(2).forEach { rowProducts ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                rowProducts.forEach { imageRes ->
                    FeaturedProductItem(imageRes = imageRes)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FeaturedProductItem(imageRes: Int) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)

        )
    }
}
