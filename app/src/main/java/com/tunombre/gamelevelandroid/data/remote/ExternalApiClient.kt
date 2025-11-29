package com.tunombre.gamelevelandroid.data.remote

import com.tunombre.gamelevelandroid.data.model.ExternalGame
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 1. La Interfaz (Define QUÉ pedimos)
interface ExternalApiService {
    // Pedimos la lista de juegos, ordenados por popularidad
    @GET("api/games?sort-by=popularity")
    suspend fun getPopularGames(): List<ExternalGame>
}

// 2. El Objeto Cliente (Define CÓMO nos conectamos)
object ExternalApiClient {
    private const val BASE_URL = "https://www.freetogame.com/"

    val service: ExternalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExternalApiService::class.java)
    }
}