package com.example.xuehanyu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.xuehanyu.core.navigation.AppNavHost
import com.example.xuehanyu.core.navigation.MainNavHost
import com.example.xuehanyu.core.theme.XueHanYuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XueHanYuTheme {
                val navController = rememberNavController()
                Scaffold {
                    innerPadding ->
                    MainNavHost(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .imePadding() // Add IME (keyboard) padding
                    )
                }
            }
        }
    }
}