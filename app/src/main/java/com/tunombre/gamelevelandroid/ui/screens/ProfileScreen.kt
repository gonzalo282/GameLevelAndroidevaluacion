package com.tunombre.gamelevelandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: GameLevelViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (currentUser == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No has iniciado sesión",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate(Screen.Login.route) }
                    ) {
                        Text("Iniciar Sesión")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Header con info del usuario
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    border = androidx.compose.foundation.BorderStroke(3.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            currentUser!!.nombre,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            currentUser!!.email,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Información del perfil
                Text(
                    "Información Personal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ProfileInfoItem(
                    icon = Icons.Default.Phone,
                    label = "Teléfono",
                    value = currentUser!!.telefono
                )
                
                ProfileInfoItem(
                    icon = Icons.Default.Home,
                    label = "Dirección",
                    value = currentUser!!.direccion
                )
                
                ProfileInfoItem(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = currentUser!!.email
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Opciones
                Text(
                    "Opciones",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    onClick = { navController.navigate(Screen.Reviews.route) },
                    modifier = Modifier.fillMaxWidth(),
                    border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, "Mis reseñas")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "Mis Reseñas",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Botón de cerrar sesión
                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión")
                }
            }
        }
    }
}

@Composable
fun ProfileInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    value,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
