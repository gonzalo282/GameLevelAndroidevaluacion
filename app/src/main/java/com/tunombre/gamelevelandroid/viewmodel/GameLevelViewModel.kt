package com.tunombre.gamelevelandroid.viewmodel

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

    // User State
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // --- ARREGLADO: 'authToken' ahora es 'private' ya que la UI no lo usa ---
    private val _authToken = MutableStateFlow<String?>(null)
    // val authToken: StateFlow<String?> = _authToken.asStateFlow() // <-- Eliminado

    // Products State
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    // Lógica de Categoría (para HomeScreen)
    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // SECCIÓN DEL CARRITO
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

    // El resto de tus variables de estado
    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()


    // Auth Methods
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

    fun register(
        nombre: String,
        email: String,
        password: String,
        telefono: String,
        direccion: String,
        onSuccess: () -> Unit
    ) {
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
        repository.clearLocalCart()
    }

    // --- 'simularLogin' ELIMINADO (Código muerto) ---

    // Products Methods
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

            _isLoading.value = false
        }
    }

    fun setCategoryFilter(category: String) {
        _selectedCategory.value = category
    }

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            if (useSampleData) {
                _selectedProduct.value = SampleProducts.getProductById(productId)
            } else {
                val result = repository.getProduct(productId)
                result.onSuccess { product ->
                    _selectedProduct.value = product
                }.onFailure { exception ->
                    _selectedProduct.value = SampleProducts.getProductById(productId)
                    _errorMessage.value = exception.message
                }
            }

            _isLoading.value = false
        }
    }

    // --- 'searchProducts' y 'filterByCategory' ELIMINADOS (Obsoletos) ---

    // --- SECCIÓN DEL CARRITO ---
    fun loadCart() {
        // Esta función está vacía a propósito,
        // porque el Flow reactivo se encarga de todo.
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            val user = _currentUser.value
            val token = _authToken.value

            if (user != null && token != null) {
                val result = repository.addToCart(product.id, quantity, user.id, token)
                result.onSuccess {
                    // No es necesario llamar a loadCart()
                }.onFailure { exception ->
                    _errorMessage.value = exception.message
                }
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
                result.onSuccess {
                    // No es necesario llamar a loadCart()
                }.onFailure { exception ->
                    _errorMessage.value = exception.message
                }
            }
        }
    }

    fun incrementCartItem(itemId: Int) {
        viewModelScope.launch {
            repository.incrementCartItem(itemId)
        }
    }

    fun decrementCartItem(itemId: Int) {
        viewModelScope.launch {
            repository.decrementCartItem(itemId)
        }
    }

    // --- 'updateCartTotal' ELIMINADO (Obsoleto) ---

    // --- Lógica de Pedidos (Checkout) ---
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

            val result = repository.createOrder(
                userId = user.id,
                items = items,
                direccion = user.direccion,
                token = token
            )

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

    // Reviews Methods
    fun loadProductReviews(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getProductReviews(productId)
            result.onSuccess { reviewsList ->
                _reviews.value = reviewsList
            }.onFailure { exception ->
                _errorMessage.value = exception.message
            }
            _isLoading.value = false
        }
    }

    // --- ARREGLADO: Añadimos @Suppress para la advertencia ---
    @Suppress("unused")
    fun addReview(productId: Int, rating: Int, comment: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = _currentUser.value
            val token = _authToken.value

            if (user != null && token != null) {
                _isLoading.value = true
                val result = repository.addReview(productId, rating, comment, user.id, token)
                result.onSuccess {
                    loadProductReviews(productId) // Recarga las reseñas
                    onSuccess()
                }.onFailure { exception ->
                    _errorMessage.value = exception.message
                }
                _isLoading.value = false
            } else {
                _errorMessage.value = "Debes iniciar sesión para dejar una reseña"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}