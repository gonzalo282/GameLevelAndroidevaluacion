# 📸 Guía: Agregar Imágenes Locales a Drawable

## Paso 1: Preparar las Imágenes

### Nombres de Archivo Requeridos (sin espacios, solo minúsculas y guiones bajos):

1. `teclado_rgb.jpg` - Teclado RGB con luces
2. `headset_hyperx.jpg` - Auriculares HyperX rojo/negro
3. `catan_board.jpg` - Tablero Catan 3D
4. `pc_gaming_setup.jpg` - PC completo con monitor
5. `consolas.jpg` - PS4, Xbox, Switch juntas
6. `juegos_populares.jpg` - Banner con 3 juegos
7. `mouse_logitech.jpg` - Mouse Logitech G502
8. `mouse_razer.jpg` - Mouse Razer con luz verde
9. `mousepad_rgb.jpg` - Mousepad con luces RGB
10. `ps5_console.jpg` - Caja PlayStation 5
11. `camiseta_retro.jpg` - Camiseta negra retro gaming
12. `hoodie_gaming.jpg` - Hoodie negro gaming
13. `hoodie_gamer.jpg` - Hoodie RGB neón
14. `silla_razer.jpg` - Sillas Razer gaming
15. `controller_mask.jpg` - Control + máscara LED azul

## Paso 2: Copiar las Imágenes

### Usando el Explorador de Windows:

1. **Abre el Explorador de Archivos**

2. **Navega a la carpeta del proyecto:**
   ```
   C:\Users\ivory\AndroidStudioProjects\GameLevelAndroid\app\src\main\res\drawable
   ```

3. **Si la carpeta `drawable` no existe:**
   - Click derecho en la carpeta `res`
   - Nueva carpeta → Nombrarla: `drawable`

4. **Copia tus imágenes:**
   - Abre la carpeta donde tienes las imágenes
   - Renombra cada imagen según la lista de arriba
   - Copia todas las imágenes a la carpeta `drawable`

### Usando Android Studio:

1. **En Android Studio, vista "Project"**
2. **Navega a:** `app → src → main → res`
3. **Click derecho en `res` → New → Directory → Nombre: `drawable`**
4. **Arrastra las imágenes** desde el Explorador de Windows a la carpeta `drawable`
5. Android Studio preguntará: **"Copy" o "Move"** - Selecciona **"Copy"**

## Paso 3: Optimizar las Imágenes (Recomendado)

Para que la app no sea muy pesada:

1. **Reduce el tamaño de las imágenes:**
   - Ancho máximo: 800px
   - Calidad JPEG: 80-85%
   - Usa herramientas como:
     - Paint (Windows) → Redimensionar
     - [TinyPNG.com](https://tinypng.com) - online
     - IrfanView - software gratuito

## Paso 4: Verificar

Deberías tener esta estructura:

```
GameLevelAndroid/
└── app/
    └── src/
        └── main/
            └── res/
                └── drawable/
                    ├── teclado_rgb.jpg
                    ├── headset_hyperx.jpg
                    ├── catan_board.jpg
                    ├── pc_gaming_setup.jpg
                    ├── consolas.jpg
                    ├── juegos_populares.jpg
                    ├── mouse_logitech.jpg
                    ├── mouse_razer.jpg
                    ├── mousepad_rgb.jpg
                    ├── ps5_console.jpg
                    ├── camiseta_retro.jpg
                    ├── hoodie_gaming.jpg
                    ├── hoodie_gamer.jpg
                    ├── silla_razer.jpg
                    └── controller_mask.jpg
```

## Paso 5: Actualizar el Código

Una vez que hayas copiado todas las imágenes, **avísame** y actualizaré el código automáticamente para usar las imágenes locales.

---

## 🎯 Mapeo Imagen → Archivo

| Imagen que tienes | Renombrar a |
|------------------|-------------|
| Teclado RGB multicolor | `teclado_rgb.jpg` |
| Headset rojo/negro | `headset_hyperx.jpg` |
| Tablero Catan 3D | `catan_board.jpg` |
| PC + Monitor completo | `pc_gaming_setup.jpg` |
| 4 consolas juntas | `consolas.jpg` |
| Banner 3 juegos | `juegos_populares.jpg` |
| Mouse Logitech negro | `mouse_logitech.jpg` |
| Mouse Razer verde | `mouse_razer.jpg` |
| Mousepad con luces | `mousepad_rgb.jpg` |
| Caja PS5 blanca | `ps5_console.jpg` |
| Camiseta negra retro | `camiseta_retro.jpg` |
| Hoodie negro gaming | `hoodie_gaming.jpg` |
| Hoodie RGB neón | `hoodie_gamer.jpg` |
| Sillas Razer | `silla_razer.jpg` |
| Control + máscara azul | `controller_mask.jpg` |

---

## ⚡ Comando Rápido (PowerShell)

Si tienes PowerShell y tus imágenes en el escritorio:

```powershell
# Navegar a la carpeta drawable
cd "C:\Users\ivory\AndroidStudioProjects\GameLevelAndroid\app\src\main\res"
mkdir drawable

# Luego copia manualmente y renombra según la tabla
```

---

**¿Ya copiaste las imágenes? Avísame para actualizar el código y que la app use las imágenes locales!** 📸
