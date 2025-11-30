package com.tunombre.gamelevelandroid.data.remote

import com.tunombre.gamelevelandroid.data.model.AuthResponse
import com.tunombre.gamelevelandroid.data.model.LoginRequest
import com.tunombre.gamelevelandroid.data.model.Product
import com.tunombre.gamelevelandroid.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SpringApiService {

    // --- AUTENTICACIÓN ---
    // Cambio: "api/..." en lugar de "/api/..."
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    // --- PRODUCTOS ---
    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>
}