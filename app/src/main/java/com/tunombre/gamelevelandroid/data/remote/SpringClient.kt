package com.tunombre.gamelevelandroid.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpringClient {

    // 10.0.2.2 es la dirección IP especial del emulador para acceder a tu PC (localhost)
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val service: SpringApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpringApiService::class.java)
    }
}