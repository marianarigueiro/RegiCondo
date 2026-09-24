package com.example.regicondo.ui.screens.atas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.regicondo.data.model.AtaAssembleia
import com.example.regicondo.viewmodel.AtaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAtaScreen(
    navController: NavController,
    ataId: String,
    viewModel: AtaViewModel = viewModel()
) {
    val atas by viewModel.atas.collectAsState()
    val existente = atas.find { it.id == ataId }

    var titulo by remember(existente) { mutableStateOf(existente?.titulo ?: "") }
    var tipo by remember(existente) { mutableStateOf(existente?.tipo ?: "Ordinária") }
    var pauta by remember(existente) { mutableStateOf(existente?.pauta ?: "") }
    var decisoes by remember(existente) { mutableStateOf(existente?.decisoes ?: "") }
    var presentesStr by remember(existente) { mutableStateOf(existente?.presentes?.toString() ?: "0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (ataId.isEmpty()) "Nova Ata" else "Editar Ata") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo (Ordinária | Extraordinária)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = pauta, onValueChange = { pauta = it }, label = { Text("Pauta") }, modifier = Modifier.fillMaxWidth().height(100.dp))
            OutlinedTextField(value = decisoes, onValueChange = { decisoes = it }, label = { Text("Decisões") }, modifier = Modifier.fillMaxWidth().height(100.dp))
            OutlinedTextField(value = presentesStr, onValueChange = { presentesStr = it }, label = { Text("Número de presentes") }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val presentes = presentesStr.toIntOrNull() ?: 0
                    val nova = (existente ?: AtaAssembleia()).copy(
                        id = ataId,
                        titulo = titulo,
                        tipo = tipo,
                        pauta = pauta,
                        decisoes = decisoes,
                        presentes = presentes,
                        data = existente?.data ?: System.currentTimeMillis()
                    )
                    viewModel.salvar(nova) { sucesso -> if (sucesso) navController.popBackStack() }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
