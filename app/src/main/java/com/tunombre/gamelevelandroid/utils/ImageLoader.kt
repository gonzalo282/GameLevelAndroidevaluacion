package com.tunombre.gamelevelandroid.utils

import android.content.Context
import androidx.annotation.DrawableRes

/**
 * Utilidad para convertir rutas de imágenes en IDs de recursos drawable
 */
object ImageLoader {
    
    /**
     * Convierte una ruta de imagen en el ID de recurso drawable correspondiente
     * Ejemplo: "drawable/teclado_rgb.jpg" -> R.drawable.teclado_rgb
     */
    @DrawableRes
    fun getDrawableResourceId(context: Context, imagePath: String): Int {
        // Si la imagen empieza con "drawable/", extraemos el nombre del archivo
        val fileName = if (imagePath.startsWith("drawable/")) {
            imagePath.removePrefix("drawable/")
        } else {
            imagePath
        }
        
        // Removemos la extensión del archivo (.jpg, .png, .webp, etc.)
        val resourceName = fileName.substringBeforeLast(".")
        
        // Obtenemos el ID del recurso desde R.drawable
        return context.resources.getIdentifier(
            resourceName,
            "drawable",
            context.packageName
        )
    }
    
    /**
     * Verifica si una imagen es local (está en drawable) o es una URL
     */
    fun isLocalImage(imagePath: String): Boolean {
        return imagePath.startsWith("drawable/")
    }
}
