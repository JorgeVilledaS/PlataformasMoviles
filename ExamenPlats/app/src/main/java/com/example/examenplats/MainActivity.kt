package com.example.examenplats

// Imports
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//Main
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CalculadoraApp()
            }
        }
    }
}

//Host del Navigation (Simple porqu solo tenemos dos pantallas)
@Composable
fun CalculadoraApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "pantalla1"
    ) {
        composable("pantalla1") {
            Pantalla1(navController)
        }
        composable("pantalla2/{operacion}") { backStackEntry ->
            val operacion = backStackEntry.arguments?.getString("operacion") ?: "Suma"
            Pantalla2(operacion, navController)
        }
    }
}

// UI de la pantalla 1
@Composable
fun Pantalla1(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botones en el centro
        Button(
            onClick = { navController.navigate("pantalla2/Suma") },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
        ) {
            Text("Suma", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("pantalla2/Resta") },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
        ) {
            Text("Resta", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Nombre hasta abajo
        Text(
            text = "Jorge Villeda - 24932",
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

// UI de la pantalla 2
@Composable
fun Pantalla2(operacion: String, navController: NavController) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = operacion,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        // Campo Num1
        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Num1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo Num2
        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Num2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Solo acepta Numeros
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Operar
        Button(
            onClick = {
                val n1 = num1.toDoubleOrNull() ?: 0.0
                val n2 = num2.toDoubleOrNull() ?: 0.0
                resultado = when(operacion) { //Lo trabajamos en Double, por eso siempre hay un decimal más.
                    "Suma" -> (n1 + n2).toString()
                    "Resta" -> (n1 - n2).toString()
                    else -> "0"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Operar", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Resultado hasta abajo
        Text(
            text = resultado,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}