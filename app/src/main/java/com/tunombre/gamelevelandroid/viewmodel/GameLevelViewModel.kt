package com.tunombre.gamelevelandroid.viewmodel

import android.net.Uri // <-- Importante
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tunombre.gamelevelandroid.data.model.*
import com.tunombre.gamelevelandroid.data.repository.GameLevelRepository
import com.tunombre.gamelevelandroid.data.local.SampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameLevelViewModel : ViewModel() {

    private val repository = GameLevelRepository
    private var useSampleData = false

    // --- User State ---
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _authToken = MutableStateFlow<String?>(null)

    // --- ¡¡¡NUEVO!!! Estado para la Foto de Perfil ---
    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri.asStateFlow()

    fun updateProfileImage(uri: Uri) {
        _profileImageUri.value = uri
    }
    // -------------------------------------------------

    // --- Products State ---
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    // --- Lógica de Categoría ---
    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // --- API Externa ---
    private val _externalGames = MutableStateFlow<List<ExternalGame>>(emptyList())
    val externalGames: StateFlow<List<ExternalGame>> = _externalGames.asStateFlow()

    // --- SECCIÓN DEL CARRITO ---
    val cartItems: StateFlow<List<CartItem>> = repository.getCartFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    val cartTotal: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.subtotal }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 0.0
    )

    val cartItemCount: StateFlow<Int> = cartItems.map { items ->
        items.sumOf { it.quantity }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 0
    )

    // --- Otros Estados ---
    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    // --- Auth Methods ---
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            val result = repository.login(email, password)
            result.onSuccess { response ->
                if (response.success) {
                    _currentUser.value = response.user
                    _authToken.value = response.token
                    onSuccess()
                } else {
                    _errorMessage.value = response.message
                }
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error de conexión"
            }
            _isLoading.value = false
        }
    }

    fun register(nombre: String, email: String, password: String, telefono: String, direccion: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            val result = repository.register(nombre, email, password, telefono, direccion)
            result.onSuccess { response ->
                if (response.success) {
                    _currentUser.value = response.user
                    _authToken.value = response.token
                    onSuccess()
                } else {
                    _errorMessage.value = response.message
                }
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error de conexión"
            }
            _isLoading.value = false
        }
    }

    fun logout() {
        _currentUser.value = null
        _authToken.value = null
        _profileImageUri.value = null // Limpiamos la foto al salir
        repository.clearLocalCart()
    }

    // --- Products Methods ---
    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getProducts()
            result.onSuccess { productList ->
                _products.value = productList
                useSampleData = false
            }.onFailure { exception ->
                _products.value = SampleProducts.sampleProducts
                useSampleData = true
                _errorMessage.value = "Modo sin conexión - Mostrando productos de ejemplo"
            }
            loadExternalGames()
            _isLoading.value = false
        }
    }

    private fun loadExternalGames() {
        viewModelScope.launch {
            val result = repository.getExternalGames()
            result.onSuccess { games -> _externalGames.value = games }
                .onFailure { println("Error cargando API externa: ${it.message}") }
        }
    }

    fun setCategoryFilter(category: String) { _selectedCategory.value = category }

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            if (useSampleData) {
                _selectedProduct.value = SampleProducts.getProductById(productId)
            } else {
                val result = repository.getProduct(productId)
                result.onSuccess { product -> _selectedProduct.value = product }
                    .onFailure { exception ->
                        _selectedProduct.value = SampleProducts.getProductById(productId)
                        _errorMessage.value = exception.message
                    }
            }
            _isLoading.value = false
        }
    }

    fun searchProducts(query: String): List<Product> {
        return _products.value.filter {
            it.nombre.contains(query, ignoreCase = true) || it.descripcion.contains(query, ignoreCase = true)
        }
    }

    fun filterByCategory(category: String): List<Product> {
        return if (category.isEmpty() || category == "Todos") {
            _products.value
        } else {
            _products.value.filter { it.categoria == category }
        }
    }

    // --- Carrito ---
    fun loadCart() { }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            val user = _currentUser.value
            val token = _authToken.value
            if (user != null && token != null) {
                val result = repository.addToCart(product.id, quantity, user.id, token)
                result.onSuccess { _successMessage.value = "¡${product.nombre} agregado al carrito!" }
                    .onFailure { exception -> _errorMessage.value = exception.message }
            } else {
                _errorMessage.value = "Debes iniciar sesión para agregar al carrito"
            }
        }
    }

    fun removeFromCart(itemId: Int) {
        viewModelScope.launch {
            val token = _authToken.value
            if (token != null) {
                val result = repository.removeFromCart(itemId, token)
                result.onSuccess { }.onFailure { exception -> _errorMessage.value = exception.message }
            }
        }
    }

    fun incrementCartItem(itemId: Int) { viewModelScope.launch { repository.incrementCartItem(itemId) } }
    fun decrementCartItem(itemId: Int) { viewModelScope.launch { repository.decrementCartItem(itemId) } }

    // --- Checkout ---
    fun createOrder(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val user = _currentUser.value
            val token = _authToken.value
            val items = cartItems.value
            if (user == null || token == null || items.isEmpty()) {
                _errorMessage.value = "Error: No se puede procesar el pedido."
                _isLoading.value = false
                return@launch
            }
            val result = repository.createOrder(user.id, items, user.direccion, token)
            result.onSuccess {
                repository.clearLocalCart()
                _isLoading.value = false
                onSuccess()
            }.onFailure { exception ->
                _errorMessage.value = exception.message
                _isLoading.value = false
            }
        }
    }

    // --- Reviews y Misc ---
    fun loadProductReviews(productId: Int) { /* Pendiente */ }
    @Suppress("unused")
    fun addReview(productId: Int, rating: Int, comment: String, onSuccess: () -> Unit) { /* Pendiente */ }
    fun clearError() { _errorMessage.value = null }
    fun clearSuccessMessage() { _successMessage.value = null }
}