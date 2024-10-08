package com.example.e_commerce.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryList(categories: List<String>, onCategorySelected: (String) -> Unit) {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(categories) { category ->
            Text(
                text = category,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onCategorySelected(category) }
            )
        }
    }
}



