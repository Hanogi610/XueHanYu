package com.example.xuehanyu.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xuehanyu.auth.presentation.SignUpScreen
import com.example.xuehanyu.auth.presentation.viewmodel.AuthViewModel
import com.example.xuehanyu.search.presentation.SearchScreen
import com.example.xuehanyu.settings.presentation.SettingsScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.xuehanyu.detail.presentation.DetailScreen
import com.example.xuehanyu.reader.presentation.ReadingScreen

object AppRoutes {
    const val ROOT = "root"
    const val SIGNUP = "signup"
    const val SEARCH = "search"
    const val SETTINGS = "settings"
    const val READER = "reader/{bookId}/{chapterId}"
    const val DETAIL = "detail/{bookId}"

    fun createReaderRoute(bookId: Long, chapterId: Long): String {
        return "reader/$bookId/$chapterId"
    }

    fun createDetailRoute(bookId: Long): String {
        return "detail/$bookId"
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.ROOT,
        modifier = modifier
    ) {
        composable(AppRoutes.ROOT) {
            AppNavHost(navController = navController)
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
        composable(
            route = AppRoutes.DETAIL,
            arguments = listOf(navArgument("bookId") { type = NavType.LongType })
        ){
            DetailScreen(
                bookId = it.arguments?.getLong("bookId") ?: 0L,
                onItemCLick = {materialId, chapterId ->
                    navController.navigate(AppRoutes.createReaderRoute(materialId, chapterId))
                },
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(
            route = AppRoutes.READER,
            arguments = listOf(
                navArgument("bookId") { type = NavType.LongType },
                navArgument("chapterId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val materialId = backStackEntry.arguments?.getLong("bookId") ?: 0L
            val chapterId = backStackEntry.arguments?.getLong("chapterId") ?: 0L
            ReadingScreen(
                materialId = materialId,
                chapterId = chapterId,
                onBack = { navController.navigateUp() },
                onChapterClick = { newBookId, newChapterId ->
                    navController.navigate(AppRoutes.createReaderRoute(newBookId, newChapterId))
                }
            )
        }
    }
}