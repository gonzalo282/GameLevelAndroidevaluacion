# 🚀 Guía Rápida de Inicio - Game Level Android

## ⚡ Inicio Rápido (5 minutos)

### 1️⃣ Iniciar el Backend

```cmd
cd C:\Users\ivory\OneDrive\Escritorio\Tarea_v2.1-20250911T170815Z-1-001(1)\Tarea_v2.1\server
npm install
npm start
```

El servidor debería estar corriendo en `http://localhost:3000`

### 2️⃣ Configurar la Conexión

Abre el archivo:
```
app/src/main/java/com/tunombre/gamelevelandroid/data/remote/RetrofitClient.kt
```

**Para Emulador**: Ya está configurado con `http://10.0.2.2:3000/`

**Para Dispositivo Físico**:
1. Averigua tu IP local con `ipconfig` en CMD
2. Cambia la URL a `http://TU_IP:3000/`
3. Asegúrate de estar en la misma red WiFi

### 3️⃣ Ejecutar la App

1. Abre el proyecto en Android Studio
2. Espera a que Gradle sincronice
3. Click en el botón **Run** (▶️) o presiona `Shift + F10`
4. Selecciona tu emulador o dispositivo
5. ¡La app se instalará y abrirá automáticamente!

## 📱 Navegación de la App

```
┌─────────────────────────────────────┐
│         PANTALLA HOME               │
│  - Logo y Bienvenida                │
│  - Botón "Ver Catálogo"             │
│  - Botón "Ver Reseñas"              │
│  - Categorías Populares             │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│       CATÁLOGO DE PRODUCTOS         │
│  - Barra de búsqueda                │
│  - Filtros por categoría            │
│  - Lista de juegos                  │
│  - Click en producto → Detalle      │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      DETALLE DEL PRODUCTO           │
│  - Imagen del juego                 │
│  - Precio y descuentos              │
│  - Stock disponible                 │
│  - Selector de cantidad             │
│  - Botón "Agregar al Carrito"       │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         CARRITO DE COMPRAS          │
│  - Lista de productos               │
│  - Total a pagar                    │
│  - Botón "Proceder al Pago"         │
└─────────────────────────────────────┘
              ↓
      ¿Está autenticado?
         NO ↓      SÍ ↓
┌──────────────┐  ┌──────────────┐
│    LOGIN     │  │   CHECKOUT   │
│  o REGISTRO  │  │   - Resumen  │
└──────────────┘  │   - Dirección│
                  │   - Confirmar│
                  └──────────────┘
                        ↓
              ┌──────────────────┐
              │ PAGO EXITOSO     │
              │ - Nº de Orden    │
              │ - Estado         │
              └──────────────────┘
```

## 🎮 Prueba la App

### Opción 1: Sin Backend (Solo UI)
Si solo quieres ver las pantallas sin conectar al servidor:
- La app mostrará las pantallas vacías o con datos de ejemplo
- Puedes navegar entre las diferentes secciones
- No podrás cargar productos reales ni hacer compras

### Opción 2: Con Backend (Funcionalidad Completa)
1. Asegúrate de que el servidor esté corriendo
2. Verifica la conexión en `RetrofitClient.kt`
3. Ejecuta la app
4. Navega al catálogo
5. Los productos se cargarán automáticamente desde el servidor

## 🧪 Flujo de Prueba Recomendado

1. **Explorar Home** (sin login)
   - Ver la pantalla de bienvenida
   - Revisar las categorías

2. **Ver Catálogo** (sin login)
   - Buscar productos
   - Filtrar por categoría
   - Ver detalles de productos

3. **Intentar Agregar al Carrito** (sin login)
   - Te pedirá que inicies sesión

4. **Crear Cuenta** (registro)
   - Nombre: "Test User"
   - Email: "test@gamelevel.com"
   - Password: "123456"
   - Teléfono: "912345678"
   - Dirección: "Calle Test 123"

5. **Agregar Productos al Carrito**
   - Selecciona varios juegos
   - Ajusta cantidades

6. **Ver Carrito**
   - Revisa los productos
   - Verifica el total

7. **Checkout**
   - Confirma dirección
   - Procede al pago

8. **Confirmación**
   - Ve el número de orden
   - Vuelve al inicio

## 🔧 Configuración Avanzada

### Cambiar Puerto del Backend

Si tu backend corre en otro puerto (ej: 5000):

```kotlin
// En RetrofitClient.kt
private const val BASE_URL = "http://10.0.2.2:5000/"
```

### Habilitar Logs de Retrofit

Ya está habilitado por defecto. Verás las peticiones HTTP en Logcat:
- Filtra por "OkHttp" para ver las llamadas a la API

### Probar en Dispositivo Físico

1. Habilita "Opciones de desarrollador" en tu Android
2. Activa "Depuración USB"
3. Conecta tu teléfono por USB
4. Acepta la autorización de depuración
5. Selecciona tu dispositivo en Android Studio
6. **IMPORTANTE**: Cambia la URL en `RetrofitClient.kt` a tu IP local

## 📊 Verificar que Todo Funciona

### ✅ Checklist

- [ ] Backend corriendo en puerto 3000
- [ ] Gradle sincronizado sin errores
- [ ] App se ejecuta en emulador/dispositivo
- [ ] Pantalla Home se muestra correctamente
- [ ] Catálogo carga productos (si backend está activo)
- [ ] Login/Registro funcionan
- [ ] Agregar al carrito funciona
- [ ] Checkout se completa

### ❌ Problemas Comunes

**"No se pueden cargar productos"**
- ✅ Verifica que el backend esté corriendo
- ✅ Revisa la URL en `RetrofitClient.kt`
- ✅ Mira Logcat para ver errores de conexión

**"App se cierra al abrir"**
- ✅ Sync Project with Gradle Files
- ✅ Clean Project
- ✅ Rebuild Project

**"No se conecta desde dispositivo físico"**
- ✅ Usa tu IP local, no localhost
- ✅ Verifica que estés en la misma WiFi
- ✅ Desactiva firewall temporalmente

## 🎯 Próximos Pasos

Una vez que la app funcione:

1. **Explora el código**
   - Revisa las pantallas en `ui/screens/`
   - Mira cómo funciona el ViewModel
   - Estudia la navegación

2. **Personaliza**
   - Cambia colores en `ui/theme/`
   - Modifica textos en `res/values/strings.xml`
   - Agrega nuevas funcionalidades

3. **Integra con tu Backend**
   - Adapta los modelos de datos
   - Ajusta los endpoints de la API
   - Implementa autenticación real

## 📞 Ayuda

Si encuentras problemas:
1. Revisa los logs en Logcat
2. Verifica la configuración del backend
3. Consulta el README.md completo
4. Limpia y reconstruye el proyecto

---

¡Disfruta desarrollando con Game Level Android! 🎮🚀
