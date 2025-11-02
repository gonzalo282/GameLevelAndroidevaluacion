package com.tunombre.gamelevelandroid.ui.screens

// --- ¡¡¡IMPORTACIONES AÑADIDAS!!! ---
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import java.util.Locale
import androidx.compose.foundation.BorderStroke // <-- Asegúrate de tener esta
import androidx.compose.foundation.layout.* // <-- ¡Esta es la importación clave!
// ------------------------------------

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tunombre.gamelevelandroid.data.model.CartItem
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel
import com.tunombre.gamelevelandroid.utils.ImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: GameLevelViewModel // Recibe el ViewModel compartido
) {
    // val context = LocalContext.current // No se usa aquí

    val cartItems by viewModel.cartItems.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCart()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Surface(
                    tonalElevation = 3.dp,
                    // --- ¡¡¡AQUÍ ESTÁ EL ARREGLO!!! ---
                    // Este modificador añade automáticamente un padding en la
                    // parte inferior igual al alto de la barra de navegación.
                    modifier = Modifier.navigationBarsPadding()
                    // ------------------------------------
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Total:",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "$${String.format(Locale.US, "%.2f", cartTotal)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (currentUser != null) {
                                    navController.navigate(Screen.Checkout.route)
                                } else {
                                    navController.navigate(Screen.Login.route)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Proceder al Pago",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
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
                        Icons.Filled.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "¡Agrega algunos productos!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate(Screen.Catalog.route) }
                    ) {
                        Text("Ver Catálogo")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onRemove = { viewModel.removeFromCart(item.id) },
                        onIncrement = { viewModel.incrementCartItem(item.id) },
                        onDecrement = { viewModel.decrementCartItem(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onRemove: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Imagen del producto
            Card(
                modifier = Modifier.size(80.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (item.product.imagen.isNotBlank()) {
                        AsyncImage(
                            model = if (ImageLoader.isLocalImage(item.product.imagen)) {
                                ImageLoader.getDrawableResourceId(context, item.product.imagen)
                            } else {
                                item.product.imagen
                            },
                            contentDescription = item.product.nombre,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    item.product.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Text(
                    "Precio: $${String.format(Locale.US, "%.2f", item.product.precioFinal)}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    "Subtotal: $${String.format(Locale.US, "%.2f", item.subtotal)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // --- SECCIÓN DE CONTROLES ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Controles de Cantidad
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        OutlinedIconButton(
                            onClick = onDecrement,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Filled.Remove, "Quitar uno")
                        }

                        Text(
                            "${item.quantity}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        OutlinedIconButton(
                            onClick = onIncrement,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Filled.Add, "Añadir uno")
                        }
                    }

                    IconButton(onClick = onRemove) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}