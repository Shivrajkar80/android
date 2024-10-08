package com.example.e_commerce.components

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_commerce.ui.theme.BlueDark
import java.lang.reflect.Modifier

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTopAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onAccountClick: () -> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                IconButton(onClick = onAccountClick) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "App Icon")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BlueDark,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                Toast.makeText(context, "Notifications", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun AppBottomNavigationBar(
    navController: NavHostController,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = index == selectedTab,
                onClick = {
                    onTabSelected(index)
                    navController.navigate(bottomNavItem.route)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (bottomNavItem.badges != 0) {
                                Badge {
                                    Text(text = bottomNavItem.badges.toString())
                                }
                            } else if (bottomNavItem.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedTab) bottomNavItem.selectedIcon else bottomNavItem.unselectedIcon,
                            contentDescription = bottomNavItem.title
                        )
                    }
                },
                label = {
                    Text(text = bottomNavItem.title)
                }
            )
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Category",
        route = "categories",
        selectedIcon = Icons.Filled.Category,
        unselectedIcon = Icons.Outlined.Category,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Cart",
        route = "cart",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Profile",
        route = "profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false,
        badges = 0
    )
)

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val hasNews: Boolean,
    val badges: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBackBar(title: String, navController: NavHostController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}