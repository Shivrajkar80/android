package com.example.e_commerce.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.e_commerce.viewmodel.ProductViewModel

@Composable
fun CategoryScreen(navController: NavHostController, productViewModel: ProductViewModel) {
    val products by productViewModel.products.collectAsState()
    val categories by productViewModel.categories.collectAsState()

    var selectedTab by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = "EZYDEALS",
                onAccountClick = { navController.navigate("profile") }
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                navController = navController,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Integrate CategoryBar here
            CategoryBar { category ->
                // Handle category selection
                productViewModel.onCategorySelected(category)
            }
            // Display products based on selected category
            ProductList(products, navController)
        }
    }
}
