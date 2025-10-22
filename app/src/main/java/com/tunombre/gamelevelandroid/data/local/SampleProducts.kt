package com.tunombre.gamelevelandroid.data.local

import com.tunombre.gamelevelandroid.data.model.Product

object SampleProducts {
    
    // URLs de imágenes de productos (puedes reemplazarlas con las tuyas)
    private const val HOODIE_GAMER = "https://i.imgur.com/hoodie_gamer.jpg"
    private const val SILLA_RAZER = "https://i.imgur.com/silla_razer.jpg"
    private const val CONTROLLER_MASK = "https://i.imgur.com/controller_mask.jpg"
    private const val TECLADO_RGB = "https://i.imgur.com/teclado_rgb.jpg"
    private const val HEADSET_HYPERX = "https://i.imgur.com/headset_hyperx.jpg"
    private const val CATAN_BOARD = "https://i.imgur.com/catan_board.jpg"
    private const val PC_GAMING_SETUP = "https://i.imgur.com/pc_gaming_setup.jpg"
    private const val CONSOLAS = "https://i.imgur.com/consolas.jpg"
    private const val JUEGOS_POPULARES = "https://i.imgur.com/juegos_populares.jpg"
    private const val MOUSE_LOGITECH = "https://i.imgur.com/mouse_logitech.jpg"
    private const val MOUSE_RAZER = "https://i.imgur.com/mouse_razer.jpg"
    private const val MOUSEPAD_RGB = "https://i.imgur.com/mousepad_rgb.jpg"
    private const val PS5_CONSOLE = "https://i.imgur.com/ps5_console.jpg"
    private const val CAMISETA_RETRO = "https://i.imgur.com/camiseta_retro.jpg"
    private const val HOODIE_GAMING = "https://i.imgur.com/hoodie_gaming.jpg"
    
    val sampleProducts = listOf(
        // Ropa y Accesorios Gaming
        Product(
            id = 1,
            nombre = "Hoodie Gamer RGB Neon",
            descripcion = "Sudadera con capucha diseño gaming RGB. Material premium, diseño neón con control y consola. Perfecta para gamers con estilo.",
            precio = 45.99,
            stock = 25,
            categoria = "Ropa",
            imagen = HOODIE_GAMER,
            descuento = 15.0
        ),
        
        Product(
            id = 2,
            nombre = "Camiseta Retro Gaming 8-Bit",
            descripcion = "Camiseta estilo retro con diseño 8-bit. Material 100% algodón, colores vibrantes estilo años 80. Para los amantes del gaming clásico.",
            precio = 24.99,
            stock = 50,
            categoria = "Ropa",
            imagen = CAMISETA_RETRO,
            descuento = 0.0
        ),
        
        Product(
            id = 3,
            nombre = "Hoodie Gamer Skull",
            descripcion = "Hoodie gaming con diseño de calavera. Estilo oscuro y moderno, perfecto para sesiones de gaming nocturnas.",
            precio = 42.99,
            stock = 30,
            categoria = "Ropa",
            imagen = HOODIE_GAMING,
            descuento = 10.0
        ),
        
        // Sillas Gaming
        Product(
            id = 4,
            nombre = "Silla Gaming Razer Iskur",
            descripcion = "Silla gaming profesional Razer Iskur. Soporte lumbar ajustable, reclinación 4D, brazos ajustables. Diseño ergonómico premium en negro y verde Razer.",
            precio = 499.99,
            stock = 8,
            categoria = "Mobiliario",
            imagen = SILLA_RAZER,
            descuento = 20.0
        ),
        
        // Periféricos
        Product(
            id = 5,
            nombre = "Teclado Mecánico RGB Gaming",
            descripcion = "Teclado mecánico completo con iluminación RGB personalizable. Switches mecánicos rojos, anti-ghosting, teclas programables. Ideal para gaming y productividad.",
            precio = 89.99,
            stock = 40,
            categoria = "Periféricos",
            imagen = TECLADO_RGB,
            descuento = 25.0
        ),
        
        Product(
            id = 6,
            nombre = "Mouse Logitech G502",
            descripcion = "Mouse gaming Logitech G502 Hero. Sensor HERO 25K, 11 botones programables, pesos ajustables, iluminación RGB. El favorito de los profesionales.",
            precio = 79.99,
            stock = 35,
            categoria = "Periféricos",
            imagen = MOUSE_LOGITECH,
            descuento = 0.0
        ),
        
        Product(
            id = 7,
            nombre = "Mouse Razer Viper RGB",
            descripcion = "Mouse gaming Razer Viper con iluminación RGB Chroma. Ultra ligero, sensor óptico 20K DPI, switches ópticos. Velocidad y precisión máximas.",
            precio = 69.99,
            stock = 28,
            categoria = "Periféricos",
            imagen = MOUSE_RAZER,
            descuento = 15.0
        ),
        
        Product(
            id = 8,
            nombre = "Mousepad RGB XL",
            descripcion = "Alfombrilla gaming extra grande con iluminación RGB perimetral. Superficie de tela micro-texturizada, base antideslizante. 900x400mm.",
            precio = 34.99,
            stock = 60,
            categoria = "Periféricos",
            imagen = MOUSEPAD_RGB,
            descuento = 0.0
        ),
        
        // Audio
        Product(
            id = 9,
            nombre = "HyperX Cloud II Wireless",
            descripcion = "Auriculares gaming inalámbricos HyperX Cloud II. Audio 7.1 surround, micrófono con cancelación de ruido, batería 30h. Rojo y negro premium.",
            precio = 149.99,
            stock = 22,
            categoria = "Audio",
            imagen = HEADSET_HYPERX,
            descuento = 10.0
        ),
        
        // Consolas
        Product(
            id = 10,
            nombre = "PlayStation 5 Digital Edition",
            descripcion = "Consola PS5 Digital Edition. SSD ultra rápido, ray tracing, hasta 120fps en 4K. Incluye control DualSense. La nueva generación de gaming.",
            precio = 499.99,
            stock = 5,
            categoria = "Consolas",
            imagen = PS5_CONSOLE,
            descuento = 0.0
        ),
        
        Product(
            id = 11,
            nombre = "Pack Consolas Retro",
            descripcion = "Colección de consolas clásicas: PS4, Xbox Series X, Nintendo Switch. Para los coleccionistas y amantes de todas las plataformas.",
            precio = 899.99,
            stock = 3,
            categoria = "Consolas",
            imagen = CONSOLAS,
            descuento = 5.0
        ),
        
        // Juegos
        Product(
            id = 12,
            nombre = "Pack Juegos AAA 2024",
            descripcion = "Bundle de los mejores juegos del año. Incluye títulos de acción, aventura y RPG. Códigos digitales para múltiples plataformas.",
            precio = 179.99,
            stock = 100,
            categoria = "Juegos",
            imagen = JUEGOS_POPULARES,
            descuento = 30.0
        ),
        
        // PC Gaming
        Product(
            id = 13,
            nombre = "PC Gaming Completo RGB",
            descripcion = "Setup gaming completo: PC con RTX, monitor 24' Full HD 144Hz, teclado RGB, mouse gaming, audífonos. Todo listo para jugar.",
            precio = 1299.99,
            stock = 4,
            categoria = "PC Gaming",
            imagen = PC_GAMING_SETUP,
            descuento = 15.0
        ),
        
        // Accesorios y Máscaras
        Product(
            id = 14,
            nombre = "Pack Controller + Máscara LED",
            descripcion = "Set gaming: Control Xbox compatible + Máscara LED neón azul. Perfecto para streamers y gamers que quieren destacar.",
            precio = 89.99,
            stock = 18,
            categoria = "Accesorios",
            imagen = CONTROLLER_MASK,
            descuento = 20.0
        ),
        
        // Juegos de Mesa
        Product(
            id = 15,
            nombre = "Catan Masterpiece Edition",
            descripcion = "Edición deluxe de Catan con tablero de madera premium y piezas 3D. El clásico juego de mesa en su mejor versión. ¡Financiado en 1 hora!",
            precio = 129.99,
            stock = 12,
            categoria = "Juegos de Mesa",
            imagen = CATAN_BOARD,
            descuento = 0.0
        )
    )
    
    fun getProductById(id: Int): Product? {
        return sampleProducts.find { it.id == id }
    }
    
    fun getProductsByCategory(category: String): List<Product> {
        return if (category == "Todos") {
            sampleProducts
        } else {
            sampleProducts.filter { it.categoria == category }
        }
    }
    
    fun searchProducts(query: String): List<Product> {
        return sampleProducts.filter {
            it.nombre.contains(query, ignoreCase = true) ||
            it.descripcion.contains(query, ignoreCase = true) ||
            it.categoria.contains(query, ignoreCase = true)
        }
    }
    
    val categories = listOf(
        "Todos",
        "Ropa",
        "Periféricos", 
        "Audio",
        "Consolas",
        "Juegos",
        "PC Gaming",
        "Mobiliario",
        "Accesorios",
        "Juegos de Mesa"
    )
}
