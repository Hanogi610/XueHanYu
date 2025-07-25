package com.example.xuehanyu.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainBottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : MainBottomNavItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    
    data object Discover : MainBottomNavItem(
        route = "discover",
        title = "Discover",
        icon = Icons.Default.Search
    )
    
    data object Flashcard : MainBottomNavItem(
        route = "flashcard",
        title = "Flashcard",
        icon = Icons.Default.Style
    )
    
    data object Dictionary : MainBottomNavItem(
        route = "dictionary",
        title = "Dictionary",
        icon = Icons.Default.MenuBook
    )
    
    data object Menu : MainBottomNavItem(
        route = "menu",
        title = "Menu",
        icon = Icons.Default.Menu
    )
}