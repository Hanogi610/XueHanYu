package com.example.xuehanyu.auth.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.xuehanyu.auth.presentation.LoginScreen
import com.example.xuehanyu.auth.presentation.SignUpScreen
import com.example.xuehanyu.auth.presentation.viewmodel.AuthViewModel
import com.example.xuehanyu.core.component.SplashScreen
import com.example.xuehanyu.core.navigation.AppRoutes
import com.example.xuehanyu.core.viewmodel.SplashViewModel
import com.example.xuehanyu.main.presentation.MainScreen
import com.example.xuehanyu.main.presentation.viewmodel.MainViewModel

@Composable
fun AuthStateManager(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val isInitializing by authViewModel.isInitializing.collectAsState()
    val isSplashVisible by splashViewModel.isSplashVisible.collectAsState()
    
    when {
        isInitializing || isSplashVisible -> {
            val progress by splashViewModel.splashProgress.collectAsState()
            SplashScreen(showProgress = true, progress = progress)
        }
        isLoggedIn -> {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainScreen(
                onLogout = {
                    mainViewModel.logout()
                },
                onSearchClick = {
                    navController.navigate(AppRoutes.SEARCH)
                },
                onSettingsClick = {
                    navController.navigate(AppRoutes.SETTINGS)
                }
            )
        }
        else -> {
            LoginScreen(
                vm = authViewModel,
                onSignUp = { navController.navigate(AppRoutes.SIGNUP) }
            )
        }
    }
} 