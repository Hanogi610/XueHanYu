package com.example.xuehanyu.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xuehanyu.auth.presentation.SignUpScreen
import com.example.xuehanyu.auth.presentation.component.AuthStateManager
import com.example.xuehanyu.auth.presentation.viewmodel.AuthViewModel
import com.example.xuehanyu.search.presentation.SearchScreen
import com.example.xuehanyu.settings.presentation.SettingsScreen
import androidx.hilt.navigation.compose.hiltViewModel

object AppRoutes {
    const val ROOT = "root"
    const val SIGNUP = "signup"
    const val SEARCH = "search"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.ROOT,
        modifier = modifier
    ) {
        composable(AppRoutes.ROOT) {
            AuthStateManager(navController = navController)
        }
        composable(AppRoutes.SIGNUP) {
            val authViewModel: AuthViewModel = hiltViewModel()
            SignUpScreen(
                vm = authViewModel,
                onSignIn = { navController.navigateUp() }
            )
        }
        composable(AppRoutes.SEARCH) {
            SearchScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(AppRoutes.SETTINGS) {
            SettingsScreen(
                onBackClick = { navController.navigateUp() },
                onLogout = {
                    // This will trigger logout and navigate back to auth
                    navController.navigateUp()
                }
            )
        }
    }
} 