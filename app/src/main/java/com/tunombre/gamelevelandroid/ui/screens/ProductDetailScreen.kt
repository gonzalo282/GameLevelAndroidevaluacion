package com.tunombre.gamelevelandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.utils.ImageLoader
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int,
    viewModel: GameLevelViewModel // Recibe el ViewModel compartido
) {
    val context = LocalContext.current

    // Observamos el producto seleccionado desde el ViewModel
    val product by viewModel.selectedProduct.collectAsState()
    val cartItemCount by viewModel.cartItemCount.collectAsState()

    // Cargamos el producto al entrar a la pantalla
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.nombre ?: "Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        // Usamos el icono correcto (AutoMirrored)
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                },
                actions = {
                    // Ícono del carrito con contador (Badge)
                    IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge { Text("$cartItemCount") }
                                }
                            }
                        ) {
                            Icon(Icons.Filled.ShoppingCart, "Carrito")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        // Si el producto es nulo (cargando o error), mostramos un loader
        if (product == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Si hay producto, mostramos el contenido
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // 1. Imagen del Producto (Grande)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    if (product!!.imagen.isNotBlank()) {
                        AsyncImage(
                            model = if (ImageLoader.isLocalImage(product!!.imagen)) {
                                ImageLoader.getDrawableResourceId(context, product!!.imagen)
                            } else {
                                product!!.imagen
                            },
                            contentDescription = product!!.nombre,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // Placeholder si no hay imagen
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                // 2. Información del Producto
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Categoría
                    AssistChip(
                        onClick = { },
                        label = { Text(product!!.categoria) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Nombre
                    Text(
                        text = product!!.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Precio y Descuento
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (product!!.descuento > 0) {
                            Text(
                                text = "$${product!!.precio}",
                                style = MaterialTheme.typography.titleMedium,
                                textDecoration = TextDecoration.LineThrough,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", product!!.precioFinal)}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error
                            ) {
                                Text("-${product!!.descuento.toInt()}%", modifier = Modifier.padding(4.dp))
                            }
                        } else {
                            Text(
                                text = "$${product!!.precio}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Descripción
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product!!.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón Agregar al Carrito
                    Button(
                        onClick = {
                            // Llamamos a la función del ViewModel
                            viewModel.addToCart(product!!)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = product!!.stock > 0
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (product!!.stock > 0) "Agregar al Carrito" else "Sin Stock",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}