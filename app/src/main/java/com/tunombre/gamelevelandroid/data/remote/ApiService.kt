package com.tunombre.gamelevelandroid.data.remote

import com.tunombre.gamelevelandroid.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Auth
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @GET("api/user/{id}")
    suspend fun getUser(
        @Path("id") userId: Int,
        @Header("Authorization") token: String
    ): Response<User>
    
    // Products
    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>
    
    @GET("api/products/{id}")
    suspend fun getProduct(@Path("id") productId: Int): Response<Product>
    
    @GET("api/products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<Product>>
    
    // Cart
    @GET("api/cart/{userId}")
    suspend fun getCart(
        @Path("userId") userId: Int,
        @Header("Authorization") token: String
    ): Response<List<CartItem>>
    
    @POST("api/cart")
    suspend fun addToCart(
        @Body item: Map<String, Any>,
        @Header("Authorization") token: String
    ): Response<ApiResponse>
    
    @DELETE("api/cart/{itemId}")
    suspend fun removeFromCart(
        @Path("itemId") itemId: Int,
        @Header("Authorization") token: String
    ): Response<ApiResponse>
    
    @PUT("api/cart/{itemId}")
    suspend fun updateCartItem(
        @Path("itemId") itemId: Int,
        @Body quantity: Map<String, Int>,
        @Header("Authorization") token: String
    ): Response<ApiResponse>
    
    // Reviews
    @GET("api/reviews/product/{productId}")
    suspend fun getProductReviews(@Path("productId") productId: Int): Response<List<Review>>
    
    @POST("api/reviews")
    suspend fun addReview(
        @Body review: Map<String, Any>,
        @Header("Authorization") token: String
    ): Response<ApiResponse>
    
    // Orders
    @POST("api/orders")
    suspend fun createOrder(
        @Body order: Map<String, Any>,
        @Header("Authorization") token: String
    ): Response<Order>
    
    @GET("api/orders/user/{userId}")
    suspend fun getUserOrders(
        @Path("userId") userId: Int,
        @Header("Authorization") token: String
    ): Response<List<Order>>
}

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)
