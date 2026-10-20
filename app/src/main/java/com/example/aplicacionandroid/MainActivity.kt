package com.example.aplicacionandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionandroid.ui.theme.AplicacionAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionAndroidTheme {
                val rootNavController = rememberNavController()

                // NavHost principal que gestiona Login y Pantalla Principal
                NavHost(
                    navController = rootNavController,
                    startDestination = "login"
                ) {
                    // 1. Pantalla Login
                    composable("login") {
                        LoginScreen(onLoginSuccess = {
                            // Al iniciar sesión, viajamos a "main" y quitamos "login" del historial
                            rootNavController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        })
                    }

                    // 2. Pantalla Principal (Contiene la barra navegadora)
                    composable("main") {
                        MainScreenWithBottomNav()
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. Pantalla de Inicio de Sesión
// ---------------------------------------------------------------------------
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario / Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                    onLoginSuccess() // Pasa a la pantalla principal
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Iniciar Sesión")
        }
    }
}

// ---------------------------------------------------------------------------
// 2. Estructura de Datos para los elementos del menú usando IMÁGENES (res/drawable)
// ---------------------------------------------------------------------------
data class NavigationItemConFoto(
    val title: String,
    val imageResId: Int, // ID de la foto en drawable (ej: R.drawable.bob)
    val route: String
)

@Composable
fun MainScreenWithBottomNav() {
    val navController = rememberNavController()

    // Sustituye R.drawable.ic_launcher_foreground por las fotos que tengas en tu res/drawable
    // (Por ejemplo: R.drawable.bob, R.drawable.patricio, etc.)
    val navItems = listOf(
        NavigationItemConFoto("Pantalla 1", R.drawable.ic_launcher_foreground, "pantalla1"),
        NavigationItemConFoto("Pantalla 2", R.drawable.ic_launcher_foreground, "pantalla2"),
        NavigationItemConFoto("Pantalla 3", R.drawable.ic_launcher_foreground, "pantalla3"),
        NavigationItemConFoto("Pantalla 4", R.drawable.ic_launcher_foreground, "pantalla4")
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            // Aquí usamos la imagen de res/drawable
                            Image(
                                painter = painterResource(id = item.imageResId),
                                contentDescription = item.title,
                                modifier = Modifier.size(28.dp)
                            )
                        },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pantalla1",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("pantalla1") { PantallaContenido("Vista 1") }
            composable("pantalla2") { PantallaContenido("Vista 2") }
            composable("pantalla3") { PantallaContenido("Vista 3") }
            composable("pantalla4") { PantallaContenido("Vista 4") }
        }
    }
}

@Composable
fun PantallaContenido(titulo: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = titulo, style = MaterialTheme.typography.titleLarge)
    }
}
