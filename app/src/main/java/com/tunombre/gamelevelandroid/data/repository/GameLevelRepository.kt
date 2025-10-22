package com.tunombre.gamelevelandroid.data.repository

import com.tunombre.gamelevelandroid.data.model.*
import com.tunombre.gamelevelandroid.data.remote.ApiResponse
import com.tunombre.gamelevelandroid.data.remote.RetrofitClient

class GameLevelRepository {
    
    private val apiService = RetrofitClient.apiService
    
    // Auth
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de login: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(
        nombre: String,
        email: String,
        password: String,
        telefono: String,
        direccion: String
    ): Result<AuthResponse> {
        return try {
            val request = RegisterRequest(nombre, email, password, telefono, direccion)
            val response = apiService.register(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de registro: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUser(userId: Int, token: String): Result<User> {
        return try {
            val response = apiService.getUser(userId, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error obteniendo usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Products
    suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error cargando productos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getProduct(productId: Int): Result<Product> {
        return try {
            val response = apiService.getProduct(productId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error cargando producto"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getProductsByCategory(category: String): Result<List<Product>> {
        return try {
            val response = apiService.getProductsByCategory(category)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error cargando categoría"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Cart
    suspend fun getCart(userId: Int, token: String): Result<List<CartItem>> {
        return try {
            val response = apiService.getCart(userId, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error cargando carrito"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addToCart(productId: Int, quantity: Int, userId: Int, token: String): Result<ApiResponse> {
        return try {
            val item = mapOf(
                "productId" to productId,
                "quantity" to quantity,
                "userId" to userId
            )
            val response = apiService.addToCart(item, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error agregando al carrito"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun removeFromCart(itemId: Int, token: String): Result<ApiResponse> {
        return try {
            val response = apiService.removeFromCart(itemId, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error eliminando del carrito"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Reviews
    suspend fun getProductReviews(productId: Int): Result<List<Review>> {
        return try {
            val response = apiService.getProductReviews(productId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error cargando reseñas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addReview(productId: Int, rating: Int, comment: String, userId: Int, token: String): Result<ApiResponse> {
        return try {
            val review = mapOf(
                "productId" to productId,
                "rating" to rating,
                "comment" to comment,
                "userId" to userId
            )
            val response = apiService.addReview(review, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error agregando reseña"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Orders
    suspend fun createOrder(userId: Int, items: List<CartItem>, direccion: String, token: String): Result<Order> {
        return try {
            val order = mapOf(
                "userId" to userId,
                "items" to items,
                "direccionEnvio" to direccion
            )
            val response = apiService.createOrder(order, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error creando orden"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserOrders(userId: Int, token: String): Result<List<Order>> {
        return try {
            val response = apiService.getUserOrders(userId, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error cargando órdenes"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
