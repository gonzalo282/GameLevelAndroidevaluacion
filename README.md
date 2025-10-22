# Game Level Android App

Esta es la aplicación móvil Android para Game Level, una tienda de videojuegos.

## Características

### ✅ Implementadas

- **Pantalla Principal (Home)**: Bienvenida y navegación principal
- **Catálogo de Productos**: Listado de juegos con búsqueda y filtros por categoría
- **Detalle de Producto**: Información detallada, precios, stock y opción de agregar al carrito
- **Carrito de Compras**: Gestión de productos seleccionados
- **Login y Registro**: Autenticación de usuarios
- **Perfil de Usuario**: Información personal y gestión de cuenta
- **Reseñas**: Visualización de reseñas de productos
- **Checkout**: Proceso de pago y confirmación de pedido
- **Resultado de Pago**: Confirmación de compra exitosa

### 🏗️ Arquitectura

- **MVVM (Model-View-ViewModel)**: Separación de responsabilidades
- **Jetpack Compose**: UI moderna y declarativa
- **Navigation Component**: Navegación entre pantallas
- **Retrofit**: Comunicación con API REST
- **Coroutines y Flow**: Manejo asíncrono y estado reactivo
- **Coil**: Carga de imágenes
- **Material Design 3**: Diseño moderno y consistente

## Configuración

### 1. Backend

Antes de ejecutar la app, debes tener el servidor backend corriendo. Desde la carpeta del proyecto web:

```bash
cd Tarea_v2.1/server
npm install
npm start
```

El servidor debe estar corriendo en el puerto 3000.

### 2. Configurar la URL del Backend

En el archivo `RetrofitClient.kt`, actualiza la BASE_URL:

- **Para Emulador Android**: `http://10.0.2.2:3000/`
- **Para Dispositivo Físico**: `http://TU_IP_LOCAL:3000/`

Para encontrar tu IP local en Windows:
```cmd
ipconfig
```
Busca la "Dirección IPv4" de tu adaptador de red.

### 3. Sincronizar Gradle

1. Abre el proyecto en Android Studio
2. Espera a que Gradle sincronice automáticamente
3. Si hay problemas, haz clic en "Sync Now" o "Sync Project with Gradle Files"

## Estructura del Proyecto

```
app/src/main/java/com/tunombre/gamelevelandroid/
├── data/
│   ├── model/          # Modelos de datos (User, Product, CartItem, etc.)
│   ├── remote/         # API Service y Retrofit Client
│   └── repository/     # Repositorio para acceso a datos
├── navigation/         # Configuración de navegación
├── ui/
│   ├── screens/        # Pantallas de la app
│   └── theme/          # Tema y estilos
├── viewmodel/          # ViewModels (lógica de negocio)
└── MainActivity.kt     # Actividad principal
```

## Dependencias Principales

```kotlin
// Jetpack Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose")

// Retrofit (API REST)
implementation("com.squareup.retrofit2:retrofit")
implementation("com.squareup.retrofit2:converter-gson")

// Coil (Carga de imágenes)
implementation("io.coil-kt:coil-compose")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
```

## Uso de la App

### Flujo de Usuario

1. **Inicio**: La app abre en la pantalla Home
2. **Explorar Catálogo**: 
   - Click en "Ver Catálogo"
   - Buscar productos
   - Filtrar por categorías
3. **Agregar al Carrito**: 
   - Click en un producto para ver detalles
   - Seleccionar cantidad
   - "Agregar al Carrito"
4. **Login/Registro**:
   - Para finalizar compra, debes estar autenticado
   - Crear cuenta nueva o iniciar sesión
5. **Checkout**:
   - Revisar carrito
   - "Proceder al Pago"
   - Confirmar dirección
   - Finalizar compra

## Pantallas

### Home Screen
- Banner de bienvenida
- Acceso rápido al catálogo y reseñas
- Grid de categorías populares
- Información de la tienda

### Catalog Screen
- Lista de productos con imágenes
- Barra de búsqueda
- Filtros por categoría
- Agregar productos al carrito directamente

### Product Detail Screen
- Imagen grande del producto
- Nombre, categoría y descripción
- Precio (con descuentos si aplica)
- Stock disponible
- Selector de cantidad
- Botón "Agregar al Carrito"

### Cart Screen
- Lista de productos en el carrito
- Cantidad y subtotales
- Total del pedido
- Eliminar productos
- Botón "Proceder al Pago"

### Login/Register Screens
- Formularios de autenticación
- Validación de campos
- Mensajes de error
- Navegación entre login y registro

### Profile Screen
- Información del usuario
- Datos personales (nombre, email, teléfono, dirección)
- Opción de cerrar sesión
- Acceso a reseñas

### Checkout Screen
- Resumen del pedido
- Información de envío
- Método de pago
- Confirmación de compra

### Payment Result Screen
- Confirmación de pago exitoso
- Número de orden
- Estado del pedido
- Tiempo estimado de entrega

## API Endpoints Utilizados

```
POST /api/login              # Iniciar sesión
POST /api/register           # Crear cuenta
GET  /api/products           # Obtener todos los productos
GET  /api/products/:id       # Obtener producto específico
GET  /api/cart/:userId       # Obtener carrito del usuario
POST /api/cart               # Agregar producto al carrito
DELETE /api/cart/:itemId     # Eliminar del carrito
GET  /api/reviews/:productId # Obtener reseñas de producto
POST /api/orders             # Crear orden de compra
```

## Problemas Comunes y Soluciones

### Error de Conexión al Backend

**Problema**: La app no puede conectarse al servidor

**Solución**:
1. Verifica que el servidor backend esté corriendo
2. Confirma la URL correcta en `RetrofitClient.kt`
3. Para dispositivos físicos, asegúrate de estar en la misma red WiFi
4. Verifica que el firewall permita conexiones al puerto 3000

### Gradle Sync Fallido

**Problema**: Error al sincronizar Gradle

**Solución**:
1. File → Invalidate Caches / Restart
2. Borrar carpetas `.gradle` y `build`
3. Sync Project with Gradle Files

### Imágenes no cargan

**Problema**: Las imágenes de productos no se muestran

**Solución**:
1. Verifica permisos de INTERNET en AndroidManifest.xml
2. Asegúrate de que las URLs de imágenes sean válidas
3. Comprueba la conexión a internet del dispositivo/emulador

## Próximas Mejoras

- [ ] Integración real con pasarela de pagos (Transbank)
- [ ] Persistencia local de datos (Room Database)
- [ ] Notificaciones push
- [ ] Sistema de favoritos
- [ ] Historial de órdenes
- [ ] Sistema de puntos/recompensas
- [ ] Modo offline
- [ ] Soporte para múltiples idiomas
- [ ] Modo oscuro mejorado
- [ ] Animaciones avanzadas

## Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación
- **Jetpack Compose**: Framework de UI
- **Material Design 3**: Sistema de diseño
- **Retrofit**: Cliente HTTP
- **OkHttp**: Networking
- **Gson**: Serialización JSON
- **Coil**: Carga de imágenes
- **Coroutines**: Programación asíncrona
- **Flow**: Manejo de estado reactivo
- **Navigation Component**: Navegación
- **ViewModel**: Gestión de estado UI

## Requisitos del Sistema

- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 11 o superior
- SDK de Android: mínimo API 24 (Android 7.0)
- SDK de Android: objetivo API 36 (Android 15)
- Gradle 8.11.2

## Autor

Desarrollado para el proyecto Game Level

## Licencia

Este proyecto es parte de un trabajo académico/educativo.
