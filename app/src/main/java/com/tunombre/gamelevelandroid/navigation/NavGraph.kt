package com.tunombre.gamelevelandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tunombre.gamelevelandroid.ui.screens.*
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel // <-- Asegúrate de importar esto

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: GameLevelViewModel // <-- 1. Aceptamos el ViewModel compartido
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            // --- ¡¡¡AQUÍ ESTÁ EL ARREGLO!!! ---
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Catalog.route) {
            CatalogScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                navController = navController,
                productId = productId,
                viewModel = viewModel
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Reviews.route) {
            ReviewsScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Checkout.route) {
            CheckoutScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.PaymentResult.route) {
            // Esta pantalla no parece necesitar el ViewModel
            PaymentResultScreen(navController = navController)
        }
    }
}