package com.tunombre.gamelevelandroid.ui.screens

// --- ¡¡¡IMPORTACIONES AÑADIDAS!!! ---
// (Asegúrate de que todas estas estén presentes)
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel
import java.util.Locale // <-- Importación para el formato de moneda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    viewModel: GameLevelViewModel // <-- Recibe el ViewModel compartido
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()

    // --- ¡¡¡NUEVOS ESTADOS AÑADIDOS!!! ---
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    // ------------------------------------

    // Iniciamos la dirección desde el usuario
    var direccion by remember(currentUser) {
        mutableStateOf(currentUser?.direccion ?: "")
    }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmar Pedido") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        // --- ARREGLADO (Icono Deprecated) ---
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
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
                .padding(16.dp)
        ) {
            // Resumen del pedido
            Text(
                "Resumen del Pedido",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "${cartItems.size} productos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    cartItems.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "${item.product.nombre} x${item.quantity}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                // --- ARREGLADO (Formato de moneda) ---
                                "$${String.format(Locale.US, "%.2f", item.subtotal)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total:",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            // --- ARREGLADO (Formato de moneda) ---
                            "$${String.format(Locale.US, "%.2f", cartTotal)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Información de envío
            Text(
                "Información de Envío",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = currentUser?.nombre ?: "",
                onValueChange = {},
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = currentUser?.email ?: "",
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección de Envío") },
                leadingIcon = { Icon(Icons.Filled.Home, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Método de pago
            Text(
                "Método de Pago",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Payment, // <-- Icono cambiado
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            "Pago en Línea (Simulado)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Tarjeta de crédito/débito",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de confirmar pedido
            Button(
                onClick = {
                    viewModel.clearError() // Limpia errores antiguos
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = direccion.isNotBlank() && !isLoading // Deshabilitado si está cargando
            ) {
                Icon(Icons.Filled.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Confirmar y Pagar",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // --- ¡¡¡DIÁLOGO DE CONFIRMACIÓN MODIFICADO!!! ---
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { if (!isLoading) showDialog = false },
                title = { Text("Confirmar Pedido") },
                text = {
                    // El contenido del diálogo cambia según el estado
                    when {
                        isLoading -> {
                            // 1. Muestra un spinner mientras se procesa
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        errorMessage != null -> {
                            // 2. Muestra un error si algo falla
                            Text(
                                "Error: $errorMessage",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        else -> {
                            // 3. Muestra la confirmación
                            Text("¿Deseas confirmar tu pedido por $${String.format(Locale.US, "%.2f", cartTotal)}?")
                        }
                    }
                },
                confirmButton = {
                    Button(
                        // Deshabilita el botón si está cargando
                        enabled = !isLoading,
                        onClick = {
                            // 1. Llama a la función del ViewModel
                            viewModel.createOrder(
                                // 2. El ViewModel llamará a 'onSuccess' cuando termine
                                onSuccess = {
                                    showDialog = false
                                    navController.navigate(Screen.PaymentResult.route) {
                                        // 3. Limpia el historial para no volver aquí
                                        popUpTo(Screen.Home.route)
                                    }
                                }
                            )
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(
                        // Deshabilita el botón si está cargando
                        enabled = !isLoading,
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}