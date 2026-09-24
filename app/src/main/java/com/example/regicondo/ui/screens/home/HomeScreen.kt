package com.example.regicondo.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.regicondo.ui.navigation.Tela

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("RegiCondo") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CartaoMenu("Cláusulas do regimento") {
                navController.navigate(Tela.ListaClausulas.rota)
            }
            CartaoMenu("Atas de assembleia") {
                navController.navigate(Tela.ListaAtas.rota)
            }
            CartaoMenu("Histórico de aprovações") {
                navController.navigate(Tela.ListaHistorico.rota)
            }
        }
    }
}

@Composable
private fun CartaoMenu(titulo: String, aoClicar: () -> Unit) {
    Card(
        onClick = aoClicar,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = titulo,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}