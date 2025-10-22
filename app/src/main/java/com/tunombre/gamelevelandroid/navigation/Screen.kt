package com.tunombre.gamelevelandroid.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object Catalog : Screen("catalog")
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: Int) = "product/$productId"
    }
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Reviews : Screen("reviews")
    object Checkout : Screen("checkout")
    object PaymentResult : Screen("payment_result")
}
