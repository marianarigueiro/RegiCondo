package com.example.regicondo.ui.screens.clausulas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.regicondo.data.model.ClausulaCondominial
import com.example.regicondo.viewmodel.ClausulaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormClausulaScreen(
    navController: NavController,
    clausulaId: String,
    viewModel: ClausulaViewModel = viewModel()
) {
    val clausulas by viewModel.clausulas.collectAsState()
    val existente = clausulas.find { it.id == clausulaId }

    var titulo by remember(existente) { mutableStateOf(existente?.titulo ?: "") }
    var categoria by remember(existente) { mutableStateOf(existente?.categoria ?: "") }
    var descricao by remember(existente) { mutableStateOf(existente?.descricao ?: "") }
    var status by remember(existente) { mutableStateOf(existente?.status ?: "Vigente") }

    Scaffold(
        topBar = { TopAppBar(title = { Text(if (clausulaId.isEmpty()) "Nova cláusula" else "Editar cláusula") }) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoria") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth().height(140.dp)
            )

            Text("Status", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Vigente", "Em revisão", "Revogada").forEach { opcao ->
                    FilterChip(selected = status == opcao, onClick = { status = opcao }, label = { Text(opcao) })
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val nova = (existente ?: ClausulaCondominial()).copy(
                        id = clausulaId,
                        titulo = titulo,
                        categoria = categoria,
                        descricao = descricao,
                        status = status,
                        dataAtualizacao = System.currentTimeMillis()
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