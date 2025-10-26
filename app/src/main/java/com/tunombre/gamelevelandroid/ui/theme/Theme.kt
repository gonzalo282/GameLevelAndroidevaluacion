package com.tunombre.gamelevelandroid.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Game Level Dark Theme - Esquema de colores principal
private val GameLevelDarkColorScheme = darkColorScheme(
    // Colores principales
    primary = ElectricBlue,              // Azul eléctrico para botones principales
    onPrimary = White,                   // Texto sobre botones azules
    primaryContainer = DarkGray,         // Contenedores con acento azul
    onPrimaryContainer = ElectricBlue,   // Texto en contenedores azules
    
    // Colores secundarios
    secondary = NeonGreen,               // Verde neón para acciones secundarias
    onSecondary = Black,                 // Texto sobre botones verdes
    secondaryContainer = MediumGray,     // Contenedores con acento verde
    onSecondaryContainer = NeonGreen,    // Texto en contenedores verdes
    
    // Colores terciarios
    tertiary = LightGray,                // Gris claro para elementos terciarios
    onTertiary = Black,                  // Texto sobre elementos terciarios
    tertiaryContainer = DarkGray,        // Contenedores terciarios
    onTertiaryContainer = LightGray,     // Texto en contenedores terciarios
    
    // Fondos y superficies
    background = Black,                  // Fondo principal negro
    onBackground = White,                // Texto principal blanco
    surface = DarkGray,                  // Superficies (cards, etc) gris oscuro
    onSurface = White,                   // Texto sobre superficies
    surfaceVariant = MediumGray,         // Variante de superficie
    onSurfaceVariant = LightGray,        // Texto secundario gris claro
    
    // Colores de contorno
    outline = LightGray,                 // Bordes y líneas
    outlineVariant = DarkGray,           // Bordes sutiles
    
    // Colores de error
    error = ErrorRed,                    // Rojo para errores
    onError = White,                     // Texto sobre error
    errorContainer = DarkerGray,         // Contenedor de error
    onErrorContainer = ErrorRed,         // Texto en contenedor de error
    
    // Colores de superficie con tonos
    surfaceTint = ElectricBlue,          // Tinte azul para superficies elevadas
    inverseSurface = White,              // Superficie inversa
    inverseOnSurface = Black,            // Texto sobre superficie inversa
    inversePrimary = ElectricBlue,       // Primary inverso
    
    // Scrim para modales y overlays
    scrim = Black.copy(alpha = 0.7f)     // Overlay oscuro
)

// Esquema de colores claro (opcional, por si el usuario prefiere tema claro)
private val GameLevelLightColorScheme = lightColorScheme(
    primary = ElectricBlue,
    onPrimary = White,
    primaryContainer = LightGray,
    onPrimaryContainer = Black,
    
    secondary = NeonGreen,
    onSecondary = Black,
    secondaryContainer = LightGray,
    onSecondaryContainer = Black,
    
    tertiary = DarkGray,
    onTertiary = White,
    
    background = White,
    onBackground = Black,
    surface = LightGray,
    onSurface = Black,
    surfaceVariant = LightGray,
    onSurfaceVariant = DarkGray,
    
    outline = DarkGray,
    error = ErrorRed,
    onError = White
)

@Composable
fun GameLevelAndroidTheme(
    darkTheme: Boolean = true, // Siempre modo oscuro por defecto para Game Level
    dynamicColor: Boolean = false, // Desactivamos colores dinámicos para mantener la identidad
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> GameLevelDarkColorScheme
        else -> GameLevelLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}