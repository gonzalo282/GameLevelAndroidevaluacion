package com.tunombre.gamelevelandroid.data.model

data class Product(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val categoria: String,
    val imagen: String,
    val descuento: Double
) {
    val precioFinal: Double
        get() = precio * (1 - descuento / 100)
}

data class CartItem(
    val id: Int,
    val product: Product,
    val quantity: Int
) {
    val subtotal: Double
        get() = product.precioFinal * quantity
}

data class Review(
    val id: Int,
    val productId: Int,
    val userId: Int,
    val userName: String,
    val rating: Int,
    val comment: String,
    val fecha: String
)

data class Order(
    val id: Int,
    val userId: Int,
    val items: List<CartItem>,
    val total: Double,
    val estado: String,
    val fecha: String,
    val direccionEnvio: String
)
