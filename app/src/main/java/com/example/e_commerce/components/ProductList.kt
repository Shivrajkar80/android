package com.example.e_commerce.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce.R
import com.example.e_commerce.network.Product

@Composable
fun CategoryBar(onCategoryClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategoryItem("electronics", R.drawable.phone_icon, onCategoryClick)
        CategoryItem("jewelery", R.drawable.new_jewellery, onCategoryClick)
        CategoryItem("men's clothing", R.drawable.man_icon, onCategoryClick)
        CategoryItem("women's clothing", R.drawable.womans_icon, onCategoryClick)
    }
}

@Composable
fun CategoryItem(category: String, iconResId: Int, onCategoryClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onCategoryClick(category) },
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Log.d("CategoryItem", "Displaying category: $category with icon: $iconResId")
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = category,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = category,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
        )
    }
}

@Composable
fun ProductList(products: List<Product>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductItem(product, navController)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("product_details/${product.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Red,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}