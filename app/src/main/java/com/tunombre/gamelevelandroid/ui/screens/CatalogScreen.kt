package com.tunombre.gamelevelandroid.ui.screens

// Importaciones (Asegúrate de que androidx.compose.material3.SnackbarHostState esté)
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tunombre.gamelevelandroid.data.model.Product
import com.tunombre.gamelevelandroid.data.local.SampleProducts
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel
import com.tunombre.gamelevelandroid.utils.ImageLoader
// NO importes más el 'context' de corrutinas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: GameLevelViewModel = viewModel()
) {
    // val context = LocalContext.current // Ya no es necesario aquí, se movió a ProductCard
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    // --- CÓDIGO (1/3): Para el Snackbar ---
    val errorMessage by viewModel.errorMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    // -------------------------------------------

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    val categories = SampleProducts.categories

    val filteredProducts = remember(products, searchQuery, selectedCategory) {
        var result = products
        if (selectedCategory != "Todos") {
            result = result.filter { it.categoria == selectedCategory }
        }
        if (searchQuery.isNotBlank()) {
            result = result.filter {
                it.nombre.contains(searchQuery, ignoreCase = true) ||
                        it.descripcion.contains(searchQuery, ignoreCase = true)
            }
        }
        result
    }

    LaunchedEffect(Unit) {
        // --- CÓDIGO NUEVO AÑADIDO (Paso 1.3) ---
        // ¡Llamamos a la simulación de login!
        // Esto "inicia sesión" automáticamente al abrir la pantalla.
        viewModel.simularLogin()
        // ------------------------------------------

        // Esto ya existía y carga los productos
        viewModel.loadProducts()
    }

    // --- CÓDIGO (2/3): Efecto para mostrar el Snackbar ---
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            snackbarHostState.showSnackbar(
                message = errorMessage!!,
                duration = SnackbarDuration.Short
            )
            viewModel.clearError() // Limpiamos el error para que no se muestre de nuevo
        }
    }
    // -----------------------------------------------------

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                        Icon(Icons.Default.ShoppingCart, "Carrito")
                    }
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
        },
        // --- CÓDIGO (3/3): Añadir el SnackbarHost al Scaffold ---
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        // --------------------------------------------------------
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar juegos...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, "Limpiar")
                        }
                    }
                },
                singleLine = true
            )

            // Filtro de categorías
            ScrollableTabRow(
                selectedTabIndex = categories.indexOf(selectedCategory),
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp
            ) {
                categories.forEach { category ->
                    Tab(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        text = { Text(category) }
                    )
                }
            }

            // Lista de productos
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (filteredProducts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No se encontraron productos",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            product = product,
                            onClick = {
                                navController.navigate(Screen.ProductDetail.createRoute(product.id))
                            },
                            onAddToCart = {
                                // Esta llamada ahora SÍ debería funcionar
                                viewModel.addToCart(product)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    // El 'context' se define aquí, donde se necesita
    val context: Context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Imagen del producto
            Card(
                modifier = Modifier.size(100.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (product.imagen.isNotBlank()) {
                        AsyncImage(
                            model = if (ImageLoader.isLocalImage(product.imagen)) {
                                ImageLoader.getDrawableResourceId(context, product.imagen)
                            } else {
                                product.imagen
                            },
                            contentDescription = product.nombre,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    product.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    product.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    product.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (product.descuento > 0) {
                        Text(
                            "$${product.precio}",
                            style = MaterialTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "$${product.precioFinal}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            "$${product.precio}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAddToCart,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = product.stock > 0
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (product.stock > 0) "Agregar" else "Sin Stock")
                }
            }
        }
    }
}