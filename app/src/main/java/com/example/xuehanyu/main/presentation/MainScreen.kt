package com.example.xuehanyu.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xuehanyu.main.component.MainAppBar
import com.example.xuehanyu.main.component.MainBottomNavigation
import com.example.xuehanyu.main.navigation.MainBottomNavItem
import com.example.xuehanyu.discover.presentation.DiscoverScreen
import com.example.xuehanyu.flashcard.presentation.FlashcardScreen
import com.example.xuehanyu.home.presentation.HomeScreen
import com.example.xuehanyu.dictionary.presentation.DictionaryScreen
import com.example.xuehanyu.menu.presentation.MenuScreen
import com.example.xuehanyu.main.presentation.viewmodel.MainViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.xuehanyu.core.navigation.AppRoutes

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    mainNav : NavController
) {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    
    Scaffold(
        topBar = {
            MainAppBar(
                onSearchClick = onSearchClick,
                onSettingsClick = onSettingsClick,
                modifier = modifier
            )
        },
        bottomBar = {
            MainBottomNavigation(navController = navController)
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = MainBottomNavItem.Home.route
            ) {
                composable(MainBottomNavItem.Home.route) {
                    HomeScreen(onItemClick = {materialId ->
                        mainNav.navigate(AppRoutes.createDetailRoute(materialId))
                    })
                }
                composable(MainBottomNavItem.Discover.route) {
                    DiscoverScreen()
                }
                composable(MainBottomNavItem.Flashcard.route) {
                    FlashcardScreen()
                }
                composable(MainBottomNavItem.Dictionary.route) {
                    DictionaryScreen()
                }
                composable(MainBottomNavItem.Menu.route) {
                    MenuScreen()
                }
            }
        }
    }
} 