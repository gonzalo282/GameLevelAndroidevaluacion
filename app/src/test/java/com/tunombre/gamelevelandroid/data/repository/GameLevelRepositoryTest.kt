package com.tunombre.gamelevelandroid.data.repository

import com.tunombre.gamelevelandroid.data.model.ExternalGame
import com.tunombre.gamelevelandroid.data.remote.ExternalApiClient
import com.tunombre.gamelevelandroid.data.remote.ExternalApiService
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameLevelRepositoryTest {

    private val repository = GameLevelRepository

    @Before
    fun setup() {
        repository.clearLocalCart()
        runBlocking {
            repository.getProducts()
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // --- Usamos nombres de función estándar (sin espacios) ---

    @Test
    fun addToCart_agrega_item_nuevo_correctamente() = runBlocking {
        val productId = 1
        val result = repository.addToCart(productId, 1, 0, "")

        assertTrue(result.isSuccess)

        val cartItems = repository.getCartFlow().first()
        assertEquals(1, cartItems.size)
        assertEquals(productId, cartItems[0].product.id)
    }

    @Test
    fun addToCart_incrementa_cantidad_si_existe() = runBlocking {
        val productId = 1
        repository.addToCart(productId, 1, 0, "")
        repository.addToCart(productId, 2, 0, "")

        val cartItems = repository.getCartFlow().first()
        assertEquals(1, cartItems.size)
        assertEquals(3, cartItems[0].quantity)
    }

    @Test
    fun incrementCartItem_aumenta_cantidad() = runBlocking {
        repository.addToCart(1, 1, 0, "")
        val cartItemId = repository.getCartFlow().first()[0].id

        repository.incrementCartItem(cartItemId)

        val item = repository.getCartFlow().first()[0]
        assertEquals(2, item.quantity)
    }

    @Test
    fun decrementCartItem_disminuye_cantidad() = runBlocking {
        repository.addToCart(1, 2, 0, "")
        val cartItemId = repository.getCartFlow().first()[0].id

        repository.decrementCartItem(cartItemId)

        val item = repository.getCartFlow().first()[0]
        assertEquals(1, item.quantity)
    }

    @Test
    fun decrementCartItem_elimina_si_llega_a_cero() = runBlocking {
        repository.addToCart(1, 1, 0, "")
        val cartItemId = repository.getCartFlow().first()[0].id

        repository.decrementCartItem(cartItemId)

        val cartItems = repository.getCartFlow().first()
        assertTrue(cartItems.isEmpty())
    }

    @Test
    fun removeFromCart_elimina_item() = runBlocking {
        repository.addToCart(1, 5, 0, "")
        val cartItemId = repository.getCartFlow().first()[0].id

        repository.removeFromCart(cartItemId, "")

        val cartItems = repository.getCartFlow().first()
        assertTrue(cartItems.isEmpty())
    }

    @Test
    fun clearLocalCart_vacia_todo() = runBlocking {
        repository.addToCart(1, 1, 0, "")
        repository.addToCart(2, 1, 0, "")

        repository.clearLocalCart()

        assertTrue(repository.getCartFlow().first().isEmpty())
    }

    @Test
    fun getExternalGames_devuelve_lista() = runBlocking {
        mockkObject(ExternalApiClient)
        val mockService = mockk<ExternalApiService>()

        val fakeGames = listOf(
            ExternalGame(1, "Juego Test", "url", "desc", "url", "RPG", "PC")
        )

        every { ExternalApiClient.service } returns mockService
        coEvery { mockService.getPopularGames() } returns fakeGames

        val result = repository.getExternalGames()

        assertTrue(result.isSuccess)
        assertEquals("Juego Test", result.getOrNull()?.first()?.title)
    }
}