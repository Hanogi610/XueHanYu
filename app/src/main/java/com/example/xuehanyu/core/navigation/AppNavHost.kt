package com.example.xuehanyu.core.navigation

import com.example.xuehanyu.auth.presentation.LoginScreen
import SignUpScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xuehanyu.auth.presentation.viewmodel.AuthViewModel
import androidx.hilt.navigation.compose.hiltViewModel

object AppRoutes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN,
        modifier = modifier
    ) {
        composable(AppRoutes.LOGIN) {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                vm = authViewModel,
                onSignUp = { navController.navigate(AppRoutes.SIGNUP) }
            )
        }
        composable(AppRoutes.SIGNUP) {
            val authViewModel: AuthViewModel = hiltViewModel()
            SignUpScreen(
                vm = authViewModel,
                onSignIn = { navController.navigateUp() }
            )
        }
    }
} 