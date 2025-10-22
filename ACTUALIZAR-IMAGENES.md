# 🚀 ACTUALIZAR IMÁGENES - GUÍA RÁPIDA

## ⚡ 3 Pasos Simples

### 1️⃣ Sube tus imágenes a Imgur

1. Ve a **https://imgur.com**
2. Click en **"New post"**
3. Arrastra las 15 imágenes que tienes
4. Espera que se suban

### 2️⃣ Copia las URLs

Para cada imagen:
1. Click derecho sobre la imagen
2. **"Copiar enlace de imagen"** o **"Copy image address"**
3. Guarda la URL (se verá algo como: `https://i.imgur.com/ABC123.jpg`)

### 3️⃣ Pega las URLs en el código

Abre el archivo:
```
app/src/main/java/com/tunombre/gamelevelandroid/data/local/SampleProducts.kt
```

Busca esta sección (líneas 6-20 aproximadamente):

```kotlin
// URLs de imágenes de productos (puedes reemplazarlas con las tuyas)
private const val HOODIE_GAMER = "https://i.imgur.com/hoodie_gamer.jpg"
private const val SILLA_RAZER = "https://i.imgur.com/silla_razer.jpg"
// ... etc
```

**Reemplaza** cada URL con la que copiaste de Imgur:

```kotlin
private const val HOODIE_GAMER = "https://i.imgur.com/TU_URL_1.jpg"
private const val SILLA_RAZER = "https://i.imgur.com/TU_URL_2.jpg"
private const val CONTROLLER_MASK = "https://i.imgur.com/TU_URL_3.jpg"
private const val TECLADO_RGB = "https://i.imgur.com/TU_URL_4.jpg"
private const val HEADSET_HYPERX = "https://i.imgur.com/TU_URL_5.jpg"
private const val CATAN_BOARD = "https://i.imgur.com/TU_URL_6.jpg"
private const val PC_GAMING_SETUP = "https://i.imgur.com/TU_URL_7.jpg"
private const val CONSOLAS = "https://i.imgur.com/TU_URL_8.jpg"
private const val JUEGOS_POPULARES = "https://i.imgur.com/TU_URL_9.jpg"
private const val MOUSE_LOGITECH = "https://i.imgur.com/TU_URL_10.jpg"
private const val MOUSE_RAZER = "https://i.imgur.com/TU_URL_11.jpg"
private const val MOUSEPAD_RGB = "https://i.imgur.com/TU_URL_12.jpg"
private const val PS5_CONSOLE = "https://i.imgur.com/TU_URL_13.jpg"
private const val CAMISETA_RETRO = "https://i.imgur.com/TU_URL_14.jpg"
private const val HOODIE_GAMING = "https://i.imgur.com/TU_URL_15.jpg"
```

## 📋 Correspondencia Imagen → Producto

| # | Nombre del Archivo | Producto |
|---|-------------------|----------|
| 1 | Hoodie RGB neón | HOODIE_GAMER |
| 2 | Sillas Razer negra/verde | SILLA_RAZER |
| 3 | Control + Máscara LED azul | CONTROLLER_MASK |
| 4 | Teclado RGB multicolor | TECLADO_RGB |
| 5 | Headset rojo/negro HyperX | HEADSET_HYPERX |
| 6 | Tablero Catan 3D madera | CATAN_BOARD |
| 7 | PC + Monitor + Periféricos | PC_GAMING_SETUP |
| 8 | 4 consolas (PS4, Xbox, Switch) | CONSOLAS |
| 9 | Banner 3 juegos | JUEGOS_POPULARES |
| 10 | Mouse negro Logitech | MOUSE_LOGITECH |
| 11 | Mouse Razer con luz verde | MOUSE_RAZER |
| 12 | Mousepad RGB luces colores | MOUSEPAD_RGB |
| 13 | Caja PS5 blanca | PS5_CONSOLE |
| 14 | Camiseta negra retro | CAMISETA_RETRO |
| 15 | Hoodie negro gaming | HOODIE_GAMING |

## ✅ Listo!

1. Guarda el archivo
2. Ejecuta la app
3. Ve al catálogo
4. ¡Verás tus productos con imágenes reales!

## 🎯 Tip Pro

Si quieres probar antes de subir todas las imágenes:
- Sube solo 2-3 imágenes
- Actualiza solo esas URLs
- Verifica que funcionen
- Luego sube el resto

## 🆘 ¿Problemas?

**No se ven las imágenes:**
- Verifica que la URL termine en `.jpg` o `.png`
- Abre la URL en tu navegador para confirmar que funciona
- Asegúrate de que sea `https://` (con S)

**¿Quieres usar imágenes del backend?**
- No hagas nada aquí
- El backend enviará las URLs automáticamente
- La app las cargará sin cambios

---

**Tiempo estimado:** 10 minutos ⏱️
