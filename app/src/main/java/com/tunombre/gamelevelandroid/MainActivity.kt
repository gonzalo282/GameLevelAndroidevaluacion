package com.tunombre.gamelevelandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tunombre.gamelevelandroid.navigation.NavGraph
import com.tunombre.gamelevelandroid.ui.theme.GameLevelAndroidTheme
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel
// --- ¡¡¡IMPORTACIÓN AÑADIDA!!! ---
import com.tunombre.gamelevelandroid.data.repository.GameLevelRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- ¡¡¡AQUÍ ESTÁ EL ARREGLO!!! ---
        // Inicializamos el Repositorio (y la base de datos Room)
        // usando el contexto de la aplicación ANTES de que se cree la UI.
        GameLevelRepository.init(applicationContext)
        // ---------------------------------

        enableEdgeToEdge()
        setContent {
            GameLevelAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Ahora, cuando el ViewModel se cree, el Repositorio
                    // ya estará 100% inicializado y listo para usar.
                    val viewModel: GameLevelViewModel = viewModel()

                    NavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}