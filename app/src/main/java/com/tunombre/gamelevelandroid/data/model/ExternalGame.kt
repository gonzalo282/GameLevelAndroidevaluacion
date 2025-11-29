package com.tunombre.gamelevelandroid.data.model

import com.google.gson.annotations.SerializedName

// Este modelo representa los datos que vienen de la API externa (FreeToGame)
data class ExternalGame(
    val id: Int,
    val title: String,
    @SerializedName("thumbnail") val thumbnail: String, // Mapea el JSON "thumbnail" a esta variable
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("game_url") val gameUrl: String,
    val genre: String,
    val platform: String
)