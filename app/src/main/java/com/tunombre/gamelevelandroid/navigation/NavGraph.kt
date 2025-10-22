package com.tunombre.gamelevelandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tunombre.gamelevelandroid.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        
        composable(Screen.Catalog.route) {
            CatalogScreen(navController = navController)
        }
        
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                navController = navController,
                productId = productId
            )
        }
        
        composable(Screen.Cart.route) {
            CartScreen(navController = navController)
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        
        composable(Screen.Reviews.route) {
            ReviewsScreen(navController = navController)
        }
        
        composable(Screen.Checkout.route) {
            CheckoutScreen(navController = navController)
        }
        
        composable(Screen.PaymentResult.route) {
            PaymentResultScreen(navController = navController)
        }
    }
}
