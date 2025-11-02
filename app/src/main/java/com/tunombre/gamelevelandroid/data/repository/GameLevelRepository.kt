package com.tunombre.gamelevelandroid.data.repository

import android.content.Context
import com.tunombre.gamelevelandroid.data.local.AppDatabase
import com.tunombre.gamelevelandroid.data.local.SampleProducts
import com.tunombre.gamelevelandroid.data.local.UserDao
import com.tunombre.gamelevelandroid.data.local.UserEntity
import com.tunombre.gamelevelandroid.data.model.*
import java.security.MessageDigest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Respuesta simple para acciones locales (carrito, etc.)
data class ApiResponse(val success: Boolean, val message: String)

object GameLevelRepository {

    // -------------------- ROOM (usuarios) --------------------
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    fun init(context: Context) {
        if (db == null) {
            db = AppDatabase.getInstance(context.applicationContext)
            userDao = db!!.userDao()
        }
    }

    private fun sha256(text: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(text.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // LOGIN: valida email + contraseña (hash) en Room
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        val dao = userDao ?: return Result.failure(
            IllegalStateException("DB no inicializada. Llama a GameLevelRepository.init(context).")
        )

        val entity = dao.auth(email.trim(), sha256(password))
        return if (entity != null) {
            val user = User(
                id = entity.id,
                nombre = entity.nombre,
                email = entity.email,
                telefono = entity.telefono,
                direccion = entity.direccion
            )
            val token = "token_local_${user.id}_${System.currentTimeMillis()}"
            Result.success(AuthResponse(true, "Login exitoso", user, token))
        } else {
            Result.success(AuthResponse(false, "Correo o contraseña inválidos", null, null))
        }
    }

    // REGISTER: inserta en Room si el email no existe
    suspend fun register(
        nombre: String,
        email: String,
        password: String,
        telefono: String,
        direccion: String
    ): Result<AuthResponse> {
        val dao = userDao ?: return Result.failure(
            IllegalStateException("DB no inicializada. Llama a GameLevelRepository.init(context).")
        )

        val emailTrim = email.trim()
        val existente = dao.findByEmail(emailTrim)
        if (existente != null) {
            return Result.success(AuthResponse(false, "El correo ya está registrado", null, null))
        }

        val entity = UserEntity(
            nombre = nombre.trim(),
            email = emailTrim,
            telefono = telefono.trim(),
            direccion = direccion.trim(),
            passwordHash = sha256(password)
        )

        val newId = dao.insert(entity).toInt()
        if (newId <= 0) return Result.failure(IllegalStateException("No se pudo insertar el usuario"))

        val created = dao.findById(newId)
            ?: return Result.failure(IllegalStateException("Usuario insertado pero no recuperado"))

        val user = User(
            id = created.id,
            nombre = created.nombre,
            email = created.email,
            telefono = created.telefono,
            direccion = created.direccion
        )
        val token = "token_local_${user.id}_${System.currentTimeMillis()}"
        return Result.success(AuthResponse(true, "Registro exitoso", user, token))
    }

    // GET USER: obtiene desde Room por id
    suspend fun getUser(userId: Int, token: String): Result<User> {
        val dao = userDao ?: return Result.failure(
            IllegalStateException("DB no inicializada. Llama a GameLevelRepository.init(context).")
        )
        val entity = dao.findById(userId)
            ?: return Result.failure(NoSuchElementException("Usuario no encontrado"))
        val user = User(entity.id, entity.nombre, entity.email, entity.telefono, entity.direccion)
        return Result.success(user)
    }
    // ---------------------------------------------------------

    // -------------------- Productos (local) ------------------
    private val _localCart = MutableStateFlow<List<CartItem>>(emptyList())
    private var cartIdCounter = 0
    private var _productCache = emptyList<Product>()

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
        return if (product != null) Result.success(product)
        else Result.failure(Exception("Producto no encontrado localmente"))
    }

    suspend fun getProductsByCategory(category: String): Result<List<Product>> {
        val products = SampleProducts.getProductsByCategory(category)
        return Result.success(products)
    }
    // ---------------------------------------------------------

    // -------------------- Carrito (local) --------------------
    fun getCartFlow(): Flow<List<CartItem>> = _localCart.asStateFlow()

    suspend fun addToCart(productId: Int, quantity: Int, userId: Int, token: String): Result<ApiResponse> {
        val productToAdd = _productCache.find { it.id == productId }
            ?: return Result.failure(Exception("Error local: Producto no encontrado en caché."))

        _localCart.update { currentCart ->
            val existingItem = currentCart.find { it.product.id == productId }
            if (existingItem != null) {
                currentCart.map {
                    if (it.id == existingItem.id) it.copy(quantity = it.quantity + quantity) else it
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
        _localCart.update { currentCart -> currentCart.filterNot { it.id == itemId } }
        return Result.success(ApiResponse(true, "Producto eliminado del carrito local"))
    }

    suspend fun incrementCartItem(itemId: Int): Result<ApiResponse> {
        _localCart.update { currentCart ->
            currentCart.map { if (it.id == itemId) it.copy(quantity = it.quantity + 1) else it }
        }
        return Result.success(ApiResponse(true, "Cantidad incrementada"))
    }

    suspend fun decrementCartItem(itemId: Int): Result<ApiResponse> {
        _localCart.update { currentCart ->
            val itemToUpdate = currentCart.find { it.id == itemId }
            if (itemToUpdate != null && itemToUpdate.quantity > 1) {
                currentCart.map { if (it.id == itemId) it.copy(quantity = it.quantity - 1) else it }
            } else {
                currentCart.filterNot { it.id == itemId }
            }
        }
        return Result.success(ApiResponse(true, "Cantidad decrementada/eliminada"))
    }

    /** Vacía el carrito local y resetea el contador. */
    fun clearLocalCart() {
        _localCart.value = emptyList()
        cartIdCounter = 0
    }
    // ---------------------------------------------------------

    // -------------------- Reseñas/Órdenes (simulado) --------
    suspend fun getProductReviews(productId: Int): Result<List<Review>> =
        Result.success(emptyList())

    suspend fun addReview(productId: Int, rating: Int, comment: String, userId: Int, token: String): Result<ApiResponse> =
        Result.success(ApiResponse(true, "Reseña guardada localmente (simulado)"))

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

    suspend fun getUserOrders(userId: Int, token: String): Result<List<Order>> =
        Result.success(emptyList())
    // ---------------------------------------------------------
}
