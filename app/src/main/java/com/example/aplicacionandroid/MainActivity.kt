package com.example.aplicacionandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionandroid.ui.theme.AplicacionAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionAndroidTheme() {

                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomAppBar(){
                        Row(
                            Modifier.fillMaxSize().
                            padding(start = 35.dp).
                            padding(end = 35.dp).
                            padding(top = 35.dp),
                            Arrangement.SpaceBetween) {
                            IconButton(onClick = {navController.navigate("pantalla1")}) {
                                Icon(
                                    imageVector = Icons.Default.AccessAlarm,
                                    contentDescription = "Pantalla 1",
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            IconButton(onClick = {navController.navigate("pantalla2")}) {
                                Icon(
                                    imageVector = Icons.Default.Android,
                                    contentDescription = "Androide p 2",
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            IconButton(onClick = {navController.navigate("")}) {
                                Icon(
                                    imageVector = Icons.Default.AccountBalance,
                                    contentDescription = "Androide p 3",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            IconButton(onClick = {navController.navigate("Perfil")}) {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = "Androide p 3",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    } }
                ) { innerPadding ->
                    AppNavegar(Modifier.padding(innerPadding),navController)

                }
            }
        }
    }
}

@Composable
fun AppNavegar(modifier: Modifier, navController : NavHostController){

    NavHost(
        navController = navController,
        startDestination = "pantalla1"
    ) {
        composable("pantalla1") {
            Pantalla1()//fun composable
        }
        composable("pantalla2") {
            Pantalla2()
        }

        composable("pantalla3") {
            Pantalla3()
        }
    }
}

@Composable
fun Pantalla1(){
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("hola, soy bob")
    }

}
@Composable
fun Pantalla2(){
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("adios bob")
    }
}

@Composable
fun Pantalla3(){
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("perro")
    }
}

