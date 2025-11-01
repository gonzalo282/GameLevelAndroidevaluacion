package com.tunombre.gamelevelandroid.data.repository

import com.tunombre.gamelevelandroid.data.local.SampleProducts
import com.tunombre.gamelevelandroid.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// --- DEFINICIÓN LOCAL TEMPORAL ---
data class ApiResponse(val success: Boolean, val message: String)
// ---------------------------------

object GameLevelRepository {

    // --- Base de datos local en memoria ---
    private val _localCart = MutableStateFlow<List<CartItem>>(emptyList())
    private var cartIdCounter = 0
    private var _productCache = emptyList<Product>()

    // --- Lógica de Autenticación (Simulada) ---
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        val fakeUser = User(1, "Usuario Simulado", email, "12345", "Calle Falsa 123")
        val fakeToken = "token_simulado_local_123"
        return Result.success(AuthResponse(true, "Login simulado exitoso", fakeUser, fakeToken))
    }

    suspend fun register(
        nombre: String,
        email: String,
        password: String,
        telefono: String,
        direccion: String
    ): Result<AuthResponse> {
        val fakeUser = User(2, nombre, email, telefono, direccion)
        val fakeToken = "token_simulado_local_456"
        return Result.success(AuthResponse(true, "Registro simulado exitoso", fakeUser, fakeToken))
    }

    suspend fun getUser(userId: Int, token: String): Result<User> {
        val fakeUser = User(userId, "Usuario Simulado", "test@test.com", "12345", "Calle Falsa 123")
        return Result.success(fakeUser)
    }

    // --- Lógica de Productos (100% Local) ---
    suspend fun getProducts(): Result<List<Product>> {
        return try {
            if (_productCache.isEmpty()) {
                _productCache = SampleProducts.sampleProducts
            }
            Result.success(_productCache)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProduct(productId: Int): Result<Product> {
        val product = SampleProducts.getProductById(productId)
        return if (product != null) {
            Result.success(product)
        } else {
            Result.failure(Exception("Producto no encontrado localmente"))
        }
    }

    suspend fun getProductsByCategory(category: String): Result<List<Product>> {
        val products = SampleProducts.getProductsByCategory(category)
        return Result.success(products)
    }

    // --- Lógica del Carrito (Local) ---
    fun getCartFlow(): Flow<List<CartItem>> {
        return _localCart.asStateFlow()
    }

    suspend fun addToCart(productId: Int, quantity: Int, userId: Int, token: String): Result<ApiResponse> {
        val productToAdd = _productCache.find { it.id == productId }

        if (productToAdd == null) {
            return Result.failure(Exception("Error local: Producto no encontrado en caché."))
        }

        _localCart.update { currentCart ->
            val existingItem = currentCart.find { it.product.id == productId }
            if (existingItem != null) {
                currentCart.map {
                    if (it.id == existingItem.id) {
                        it.copy(quantity = it.quantity + quantity)
                    } else {
                        it
                    }
                }
            } else {
                currentCart + CartItem(
                    id = cartIdCounter++,
                    product = productToAdd,
                    quantity = quantity
                )
            }
        }
        return Result.success(ApiResponse(true, "Producto agregado al carrito local"))
    }

    suspend fun removeFromCart(itemId: Int, token: String): Result<ApiResponse> {
        _localCart.update { currentCart ->
            currentCart.filterNot { it.id == itemId }
        }
        return Result.success(ApiResponse(true, "Producto eliminado del carrito local"))
    }

    suspend fun incrementCartItem(itemId: Int): Result<ApiResponse> {
        _localCart.update { currentCart ->
            currentCart.map {
                if (it.id == itemId) {
                    it.copy(quantity = it.quantity + 1)
                } else {
                    it
                }
            }
        }
        return Result.success(ApiResponse(true, "Cantidad incrementada"))
    }

    suspend fun decrementCartItem(itemId: Int): Result<ApiResponse> {
        _localCart.update { currentCart ->
            val itemToUpdate = currentCart.find { it.id == itemId }

            if (itemToUpdate != null && itemToUpdate.quantity > 1) {
                currentCart.map {
                    if (it.id == itemId) {
                        it.copy(quantity = it.quantity - 1)
                    } else {
                        it
                    }
                }
            } else {
                currentCart.filterNot { it.id == itemId }
            }
        }
        return Result.success(ApiResponse(true, "Cantidad decrementada/eliminada"))
    }

    // --- ¡¡¡ESTA ES LA NUEVA FUNCIÓN QUE ESTÁBAMOS AÑADIENDO!!! ---
    /**
     * Vacía el carrito local y resetea el contador.
     */
    fun clearLocalCart() {
        _localCart.value = emptyList()
        cartIdCounter = 0
    }
    // -----------------------------------------------------------

    // --- Lógica de Reseñas (Simulada) ---
    suspend fun getProductReviews(productId: Int): Result<List<Review>> {
        return Result.success(emptyList())
    }

    suspend fun addReview(productId: Int, rating: Int, comment: String, userId: Int, token: String): Result<ApiResponse> {
        return Result.success(ApiResponse(true, "Reseña guardada localmente (simulado)"))
    }

    // --- Lógica de Órdenes (Simulada) ---
    suspend fun createOrder(userId: Int, items: List<CartItem>, direccion: String, token: String): Result<Order> {
        val fakeOrder = Order(
            id = 1,
            userId = userId,
            items = items,
            total = items.sumOf { it.subtotal },
            estado = "Procesando (Simulado)",
            fecha = "2025-10-31",
            direccionEnvio = direccion
        )
        return Result.success(fakeOrder)
    }

    suspend fun getUserOrders(userId: Int, token: String): Result<List<Order>> {
        return Result.success(emptyList())
    }
}