package com.example.regicondo.ui.screens.clausulas

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
import com.example.regicondo.data.model.ClausulaCondominial
import com.example.regicondo.ui.navigation.Tela
import com.example.regicondo.viewmodel.ClausulaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaClausulasScreen(navController: NavController, viewModel: ClausulaViewModel = viewModel()) {
    val clausulas by viewModel.clausulas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cláusulas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Tela.FormClausula.criarRota()) }) {
                Icon(Icons.Default.Add, contentDescription = "Nova cláusula")
            }
        }
    ) { padding ->
        if (clausulas.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma cláusula cadastrada ainda.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(clausulas, key = { it.id }) { clausula ->
                    CartaoClausula(
                        clausula = clausula,
                        aoEditar = { navController.navigate(Tela.FormClausula.criarRota(clausula.id)) },
                        aoExcluir = { viewModel.excluir(clausula.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CartaoClausula(clausula: ClausulaCondominial, aoEditar: () -> Unit, aoExcluir: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(clausula.titulo, style = MaterialTheme.typography.titleLarge)
            Text(clausula.categoria, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
            Text(clausula.descricao, style = MaterialTheme.typography.bodyLarge, maxLines = 3)
            Spacer(Modifier.height(8.dp))
            AssistChip(onClick = {}, label = { Text(clausula.status) })
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = aoEditar) { Icon(Icons.Default.Edit, contentDescription = "Editar") }
                IconButton(onClick = aoExcluir) { Icon(Icons.Default.Delete, contentDescription = "Excluir") }
            }
        }
    }
}
