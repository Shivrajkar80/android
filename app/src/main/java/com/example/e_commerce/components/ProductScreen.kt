package com.example.e_commerce.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_commerce.viewmodel.ProductViewModel

@Composable
fun ProductsScreen(navController: NavController, productViewModel: ProductViewModel = viewModel()) {
    val products = productViewModel.products.collectAsStateWithLifecycle()
    val categories = productViewModel.categories.collectAsStateWithLifecycle()

    Column {
        CategoryList(categories.value) { category ->
            productViewModel.onCategorySelected(category)
        }
        ProductList(products.value, navController)
    }
}
