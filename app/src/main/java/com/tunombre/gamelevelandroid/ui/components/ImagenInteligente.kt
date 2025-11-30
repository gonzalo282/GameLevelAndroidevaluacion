package com.tunombre.gamelevelandroid.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagenInteligente(
    model: Uri?,
    modifier: Modifier = Modifier
) {
    if (model != null) {
        // Si hay foto (URI), la mostramos con Coil
        Image(
            painter = rememberAsyncImagePainter(model),
            contentDescription = "Foto de perfil",
            modifier = modifier
                .size(140.dp) // Tamaño fijo para la foto
                .clip(CircleShape) // Recorte circular
                .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape), // Borde
            contentScale = ContentScale.Crop
        )
    } else {
        // Si es null, mostramos el ícono por defecto
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Sin foto",
            modifier = modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colorScheme.outline, CircleShape),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}