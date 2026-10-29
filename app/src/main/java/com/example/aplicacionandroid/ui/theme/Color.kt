package com.example.aplicacionandroid.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Paleta del diseño (azul institucional + tarjetas pastel)
val FondoPantalla = Color(0xFFF4F6F9)
val AzulTitulo = Color(0xFF0D47A1)
val AzulOscuro = Color(0xFF002171)
val AzulClaro = Color(0xFFBBDEFB)
val TextoSecundario = Color(0xFF37474F)

class PaletaTarjeta(val fondo: Color, val titulo: Color)

val PaletaAzul = PaletaTarjeta(Color(0xFFE3F2FD), Color(0xFF0D47A1))
val PaletaVerde = PaletaTarjeta(Color(0xFFE8F5E9), Color(0xFF1B5E20))
val PaletaNaranja = PaletaTarjeta(Color(0xFFFFF3E0), Color(0xFFE65100))
val PaletaMorada = PaletaTarjeta(Color(0xFFF3E5F5), Color(0xFF4A148C))
val PaletasTarjeta = listOf(PaletaAzul, PaletaVerde, PaletaNaranja, PaletaMorada)
