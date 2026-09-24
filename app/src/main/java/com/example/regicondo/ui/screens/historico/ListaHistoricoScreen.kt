package com.example.regicondo.ui.screens.historico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.regicondo.data.model.HistoricoAprovacao
import com.example.regicondo.ui.navigation.Tela
import com.example.regicondo.viewmodel.HistoricoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaHistoricoScreen(navController: NavController, viewModel: HistoricoViewModel = viewModel()) {
    val historicos by viewModel.historico.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico de Aprovações") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Tela.FormHistorico.criarRota()) }) {
                Icon(Icons.Default.Add, contentDescription = "Novo Histórico")
            }
        }
    ) { padding ->
        if (historicos.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum histórico cadastrado ainda.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(historicos, key = { it.id }) { historico ->
                    CartaoHistorico(
                        historico = historico,
                        aoEditar = { navController.navigate(Tela.FormHistorico.criarRota(historico.id)) },
                        aoExcluir = { viewModel.excluir(historico.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CartaoHistorico(historico: HistoricoAprovacao, aoEditar: () -> Unit, aoExcluir: () -> Unit) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataFormatada = dateFormatter.format(Date(historico.dataAprovacao))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(historico.clausulaTitulo, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text("Data: $dataFormatada", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("Favor: ${historico.votosFavor} | Contra: ${historico.votosContra} | Abst.: ${historico.votosAbstencao}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            AssistChip(onClick = {}, label = { Text(historico.status) })
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = aoEditar) { Icon(Icons.Default.Edit, contentDescription = "Editar") }
                IconButton(onClick = aoExcluir) { Icon(Icons.Default.Delete, contentDescription = "Excluir") }
            }
        }
    }
}
