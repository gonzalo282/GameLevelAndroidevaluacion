package com.tunombre.gamelevelandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tunombre.gamelevelandroid.ui.screens.*
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel // <-- ¡Importante!

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
            // HomeScreen no necesita el ViewModel (basado en el NavGraph que me diste)
            HomeScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            // 2. Pasamos el ViewModel a LoginScreen
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Register.route) {
            // 3. Pasamos el ViewModel a RegisterScreen
            RegisterScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Catalog.route) {
            // 4. Pasamos el ViewModel a CatalogScreen
            CatalogScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            // 5. Pasamos el ViewModel a ProductDetailScreen
            ProductDetailScreen(
                navController = navController,
                productId = productId,
                viewModel = viewModel
            )
        }

        composable(Screen.Cart.route) {
            // 6. Pasamos el ViewModel a CartScreen
            CartScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Profile.route) {
            // 7. Pasamos el ViewModel a ProfileScreen
            ProfileScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Reviews.route) {
            // 8. Pasamos el ViewModel a ReviewsScreen
            ReviewsScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Checkout.route) {
            // 9. Pasamos el ViewModel a CheckoutScreen
            CheckoutScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.PaymentResult.route) {
            PaymentResultScreen(navController = navController)
        }
    }
}