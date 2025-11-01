package com.tunombre.gamelevelandroid.ui.screens

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tunombre.gamelevelandroid.navigation.Screen
import com.tunombre.gamelevelandroid.viewmodel.GameLevelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: GameLevelViewModel // Recibe el ViewModel compartido
) {
    // --- Estados de Valor ---
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // --- Estados de Error ---
    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }
    var direccionError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val passwordsMatch = password == confirmPassword

    // --- Lógica `isFormValid` ---
    val isFormValid = nombre.isNotBlank() && email.isNotBlank() &&
            password.isNotBlank() && confirmPassword.isNotBlank() &&
            telefono.isNotBlank() && direccion.isNotBlank() &&
            passwordsMatch &&
            nombreError == null && emailError == null &&
            telefonoError == null && direccionError == null &&
            passwordError == null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- CAMPO NOMBRE (Actualizado con largo mínimo) ---
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    // --- ¡¡¡LÓGICA DE VALIDACIÓN ACTUALIZADA!!! ---
                    nombreError = if (it.isBlank()) {
                        "El nombre no puede estar vacío"
                    } else if (it.length < 3) { // <-- ¡NUEVA REGLA!
                        "Debe tener al menos 3 caracteres"
                    } else {
                        null
                    }
                    // ---------------------------------------------
                },
                label = { Text("Nombre Completo") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = nombreError != null,
                supportingText = {
                    if (nombreError != null) Text(nombreError!!)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- CAMPO EMAIL (Sin cambios) ---
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = if (it.isBlank()) {
                        "El email no puede estar vacío"
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                        "Debe ser un email válido"
                    } else {
                        null
                    }
                },
                label = { Text("Correo Electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = emailError != null,
                supportingText = {
                    if (emailError != null) Text(emailError!!)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- CAMPO TELÉFONO (Sin cambios) ---
            OutlinedTextField(
                value = telefono,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } && newValue.length <= 12) {
                        telefono = newValue
                        telefonoError = if (newValue.length < 9) {
                            "Debe tener al menos 9 dígitos"
                        } else {
                            null
                        }
                    }
                },
                label = { Text("Teléfono (Ej: 912345678)") },
                leadingIcon = { Icon(Icons.Default.Phone, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = telefonoError != null,
                supportingText = {
                    if (telefonoError != null) Text(telefonoError!!)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- CAMPO DIRECCIÓN (Actualizado con largo mínimo) ---
            OutlinedTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                    // --- ¡¡¡LÓGICA DE VALIDACIÓN ACTUALIZADA!!! ---
                    direccionError = if (it.isBlank()) {
                        "La dirección no puede estar vacía"
                    } else if (it.length < 5) { // <-- ¡NUEVA REGLA!
                        "Debe tener al menos 5 caracteres"
                    } else {
                        null
                    }
                    // ---------------------------------------------
                },
                label = { Text("Dirección") },
                leadingIcon = { Icon(Icons.Default.Home, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = direccionError != null,
                supportingText = {
                    if (direccionError != null) Text(direccionError!!)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- CAMPO CONTRASEÑA (Sin cambios) ---
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = if (it.length < 6) {
                        "Debe tener al menos 6 caracteres"
                    } else {
                        null
                    }
                },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            "Mostrar contraseña"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = passwordError != null,
                supportingText = {
                    if (passwordError != null) Text(passwordError!!)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- CAMPO CONFIRMAR CONTRASEÑA (Sin cambios) ---
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = confirmPassword.isNotBlank() && !passwordsMatch,
                supportingText = {
                    if (confirmPassword.isNotBlank() && !passwordsMatch) {
                        Text("Las contraseñas no coinciden")
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Mensaje de Error del ViewModel (Sin cambios) ---
            if (errorMessage != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        errorMessage!!,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- Botón de Registrarse (Sin cambios) ---
            Button(
                onClick = {
                    viewModel.register(nombre, email, password, telefono, direccion) {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading && isFormValid
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Registrarse", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}