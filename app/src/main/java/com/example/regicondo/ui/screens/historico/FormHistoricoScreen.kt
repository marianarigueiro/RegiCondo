package com.example.regicondo.ui.screens.historico

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.regicondo.data.model.HistoricoAprovacao
import com.example.regicondo.viewmodel.HistoricoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormHistoricoScreen(
    navController: NavController,
    historicoId: String,
    viewModel: HistoricoViewModel = viewModel()
) {
    val historicos by viewModel.historico.collectAsState()
    val existente = historicos.find { it.id == historicoId }

    var clausulaTitulo by remember(existente) { mutableStateOf(existente?.clausulaTitulo ?: "") }
    var votosFavorStr by remember(existente) { mutableStateOf(existente?.votosFavor?.toString() ?: "0") }
    var votosContraStr by remember(existente) { mutableStateOf(existente?.votosContra?.toString() ?: "0") }
    var votosAbstencaoStr by remember(existente) { mutableStateOf(existente?.votosAbstencao?.toString() ?: "0") }
    var status by remember(existente) { mutableStateOf(existente?.status ?: "Pendente") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (historicoId.isEmpty()) "Novo Histórico" else "Editar Histórico") },
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
            OutlinedTextField(value = clausulaTitulo, onValueChange = { clausulaTitulo = it }, label = { Text("Título da Cláusula / Assunto") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = votosFavorStr, onValueChange = { votosFavorStr = it }, label = { Text("Votos a favor") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = votosContraStr, onValueChange = { votosContraStr = it }, label = { Text("Votos contra") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = votosAbstencaoStr, onValueChange = { votosAbstencaoStr = it }, label = { Text("Votos em abstenção") }, modifier = Modifier.fillMaxWidth())

            Text("Status", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Aprovado", "Rejeitado", "Pendente").forEach { opcao ->
                    FilterChip(selected = status == opcao, onClick = { status = opcao }, label = { Text(opcao) })
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val favor = votosFavorStr.toIntOrNull() ?: 0
                    val contra = votosContraStr.toIntOrNull() ?: 0
                    val abstencao = votosAbstencaoStr.toIntOrNull() ?: 0
                    val novo = (existente ?: HistoricoAprovacao()).copy(
                        id = historicoId,
                        clausulaTitulo = clausulaTitulo,
                        votosFavor = favor,
                        votosContra = contra,
                        votosAbstencao = abstencao,
                        status = status,
                        dataAprovacao = existente?.dataAprovacao ?: System.currentTimeMillis()
                    )
                    viewModel.salvar(novo) { sucesso -> if (sucesso) navController.popBackStack() }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
