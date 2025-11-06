package com.example.trekkingbuddy

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TrekkingNavGraph(viewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel) {
                navController.navigate("signup")
            }
        }
        composable("signup") {
            SignupScreen(viewModel) {
                navController.popBackStack()
            }
        }
    }
}


