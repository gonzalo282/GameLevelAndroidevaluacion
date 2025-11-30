package com.tunombre.gamelevelandroid.ui.screens

import android.Manifest // <-- IMPORTANTE
import android.content.pm.PackageManager // <-- IMPORTANTE
import android.net.Uri
import android.widget.Toast // <-- Para avisar si niegan el permiso
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat // <-- IMPORTANTE
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.ui.components.ImagenInteligente
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: GameLevelViewModel
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val profileImageUri by viewModel.profileImageUri.collectAsState()
    val context = LocalContext.current

    // --- 1. Lógica para crear archivo temporal ---
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    fun createTempPictureUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(imageFileName, ".jpg", context.cacheDir)
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            image
        )
    }

    // --- 2. Lanzadores (Resultados de Actividad) ---

    // A. Lanzador de la Cámara (Toma la foto)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPhotoUri != null) {
            viewModel.updateProfileImage(tempPhotoUri!!)
        }
    }

    // B. Función auxiliar para abrir la cámara (evita repetir código)
    fun abrirCamara() {
        val uri = createTempPictureUri()
        tempPhotoUri = uri
        cameraLauncher.launch(uri)
    }

    // C. ¡NUEVO! Lanzador de Permisos (Pide permiso al usuario)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Si el usuario dice "Sí", abrimos la cámara
            abrirCamara()
        } else {
            // Si dice "No", mostramos un mensaje
            Toast.makeText(context, "Se requiere permiso de cámara para tomar fotos", Toast.LENGTH_SHORT).show()
        }
    }

    // D. Lanzador de Galería (No requiere permiso en versiones modernas para GetContent)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateProfileImage(uri)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (currentUser == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Person, null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No has iniciado sesión", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { navController.navigate(Screen.Login.route) }) {
                        Text("Iniciar Sesión")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImagenInteligente(model = profileImageUri)

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            // Botón Galería
                            Button(onClick = {
                                galleryLauncher.launch("image/*")
                            }) {
                                Icon(Icons.Default.Image, null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Galería")
                            }

                            // --- ¡¡¡BOTÓN CÁMARA ACTUALIZADO!!! ---
                            Button(onClick = {
                                // 1. Verificamos si YA tenemos permiso
                                val permissionCheck = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )

                                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                    // Si ya lo tenemos, abrir directo
                                    abrirCamara()
                                } else {
                                    // Si no, pedirlo (esto muestra el popup del sistema)
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }) {
                                Icon(Icons.Default.CameraAlt, null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Cámara")
                            }
                            // --------------------------------------
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            currentUser!!.nombre,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            currentUser!!.email,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                ProfileInfoItem(Icons.Default.Phone, "Teléfono", currentUser!!.telefono)
                ProfileInfoItem(Icons.Default.Home, "Dirección", currentUser!!.direccion)
                ProfileInfoItem(Icons.Default.Email, "Email", currentUser!!.email)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Close, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión")
                }
            }
        }
    }
}

@Composable
fun ProfileInfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(value, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}