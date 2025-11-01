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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel
import com.tunombre.gamelevelandroid.utils.ImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int,
    viewModel: GameLevelViewModel) {
    val context = LocalContext.current
    val product by viewModel.selectedProduct.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var quantity by remember { mutableStateOf(1) }
    
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Compartir */ }) {
                        Icon(Icons.Default.Share, "Compartir")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading || product == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val currentProduct = product!!
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Imagen del producto
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    border = androidx.compose.foundation.BorderStroke(3.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (currentProduct.imagen.isNotBlank()) {
                            AsyncImage(
                                model = if (ImageLoader.isLocalImage(currentProduct.imagen)) {
                                    ImageLoader.getDrawableResourceId(context, currentProduct.imagen)
                                } else {
                                    currentProduct.imagen
                                },
                                contentDescription = currentProduct.nombre,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(120.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    if (currentProduct.descuento > 0) {
                        Surface(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(60.dp),
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.error
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "-${currentProduct.descuento.toInt()}%",
                                    color = MaterialTheme.colorScheme.onError,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Nombre y categoría
                    Text(
                        currentProduct.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            currentProduct.categoria,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Precio
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (currentProduct.descuento > 0) {
                            Text(
                                "$${currentProduct.precio}",
                                style = MaterialTheme.typography.titleLarge,
                                textDecoration = TextDecoration.LineThrough,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "$${currentProduct.precioFinal}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Text(
                                "$${currentProduct.precio}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Stock
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (currentProduct.stock > 0) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null,
                            tint = if (currentProduct.stock > 0) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (currentProduct.stock > 0) 
                                "En Stock (${currentProduct.stock} disponibles)" 
                            else 
                                "Sin Stock",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Divider()
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Descripción
                    Text(
                        "Descripción",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        currentProduct.descripcion,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Selector de cantidad
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Cantidad:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- }
                            ) {
                                Icon(Icons.Default.Clear, "Disminuir")
                            }
                            
                            Text(
                                quantity.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            
                            IconButton(
                                onClick = { 
                                    if (quantity < currentProduct.stock) quantity++ 
                                }
                            ) {
                                Icon(Icons.Default.Add, "Aumentar")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Botón agregar al carrito
                    Button(
                        onClick = {
                            viewModel.addToCart(currentProduct, quantity)
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = currentProduct.stock > 0
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Agregar al Carrito",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
