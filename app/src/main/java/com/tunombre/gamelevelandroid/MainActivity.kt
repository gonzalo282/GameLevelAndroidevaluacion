package com.tunombre.gamelevelandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel // <-- Importante
import androidx.navigation.compose.rememberNavController
import com.tunombre.gamelevelandroid.navigation.NavGraph
import com.tunombre.gamelevelandroid.ui.theme.GameLevelAndroidTheme
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel // <-- Importante

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameLevelAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // 1. Creamos el ViewModel aquí, UNA SOLA VEZ.
                    val viewModel: GameLevelViewModel = viewModel()

                    // 2. Se lo pasamos al NavGraph
                    NavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }



}