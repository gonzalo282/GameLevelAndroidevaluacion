package com.tunombre.gamelevelandroid.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tunombre.gamelevelandroid.R
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel

@OptIn(ExperimentalMaterial3Api::class) // <-- BadgedBox es Experimental
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GameLevelViewModel // Aceptamos el ViewModel
) {
    val currentUser by viewModel.currentUser.collectAsState()

    // --- Contador del Carrito ---
    val cartItemCount by viewModel.cartItemCount.collectAsState() // <-- NUEVO
    // ----------------------------

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Game Level") },
                actions = {
                    // --- ¡¡¡ICONO DEL CARRITO ACTUALIZADO CON BADGE!!! ---
                    IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge { Text("$cartItemCount") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, "Carrito")
                        }
                    }
                    // -----------------------------------------------------
                    IconButton(onClick = {
                        if (currentUser != null) {
                            navController.navigate(Screen.Profile.route)
                        } else {
                            navController.navigate(Screen.Login.route)
                        }
                    }) {
                        Icon(Icons.Default.Person, "Perfil")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo o Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Bienvenido a Game Level",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Tu tienda de videojuegos",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones principales
            Button(
                onClick = {
                    viewModel.setCategoryFilter("Todos") // Resetea el filtro
                    navController.navigate(Screen.Catalog.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver Catálogo", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.Reviews.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(Icons.Default.Star, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver Reseñas", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sección de categorías
            Text(
                "Categorías Populares",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pasamos el ViewModel al Grid
            CategoryGrid(navController, viewModel)

            Spacer(modifier = Modifier.height(32.dp))

            // Sección de información
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "¿Por qué elegirnos?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    FeatureItem(
                        icon = Icons.Default.Check,
                        text = "Amplio catálogo de juegos"
                    )
                    FeatureItem(
                        icon = Icons.Default.Check,
                        text = "Entregas rápidas y seguras"
                    )
                    FeatureItem(
                        icon = Icons.Default.Check,
                        text = "Precios competitivos"
                    )
                    FeatureItem(
                        icon = Icons.Default.Check,
                        text = "Atención al cliente 24/7"
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryGrid(
    navController: NavController,
    viewModel: GameLevelViewModel // Acepta el ViewModel
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryCard(
                title = "Periféricos",
                icon = Icons.Default.Star,
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setCategoryFilter("Periféricos")
                    navController.navigate(Screen.Catalog.route)
                }
            )
            CategoryCard(
                title = "Consolas",
                icon = Icons.Default.Favorite,
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setCategoryFilter("Consolas")
                    navController.navigate(Screen.Catalog.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryCard(
                title = "PC Gaming",
                icon = Icons.Default.Star,
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setCategoryFilter("PC Gaming")
                    navController.navigate(Screen.Catalog.route)
                }
            )
            CategoryCard(
                title = "Audio",
                icon = Icons.Default.Star,
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setCategoryFilter("Audio")
                    navController.navigate(Screen.Catalog.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryCard(
                title = "Ropa",
                icon = Icons.Default.Star,
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setCategoryFilter("Ropa")
                    navController.navigate(Screen.Catalog.route)
                }
            )
            CategoryCard(
                title = "Juegos",
                icon = Icons.Default.Star,
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setCategoryFilter("Juegos")
                    navController.navigate(Screen.Catalog.route)
                }
            )
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(100.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun FeatureItem(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}