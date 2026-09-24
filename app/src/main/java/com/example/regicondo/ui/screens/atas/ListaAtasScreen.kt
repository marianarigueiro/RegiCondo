package com.example.regicondo.ui.screens.atas

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
import com.example.regicondo.data.model.AtaAssembleia
import com.example.regicondo.ui.navigation.Tela
import com.example.regicondo.viewmodel.AtaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaAtasScreen(navController: NavController, viewModel: AtaViewModel = viewModel()) {
    val atas by viewModel.atas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atas de Assembleia") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Tela.FormAta.criarRota()) }) {
                Icon(Icons.Default.Add, contentDescription = "Nova Ata")
            }
        }
    ) { padding ->
        if (atas.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma ata cadastrada ainda.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(atas, key = { it.id }) { ata ->
                    CartaoAta(
                        ata = ata,
                        aoEditar = { navController.navigate(Tela.FormAta.criarRota(ata.id)) },
                        aoExcluir = { viewModel.excluir(ata.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CartaoAta(ata: AtaAssembleia, aoEditar: () -> Unit, aoExcluir: () -> Unit) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataFormatada = dateFormatter.format(Date(ata.data))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(ata.titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text("Tipo: ${ata.tipo} | Data: $dataFormatada", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("Pauta: ${ata.pauta}", style = MaterialTheme.typography.bodyLarge, maxLines = 2)
            Spacer(Modifier.height(8.dp))
            Text("Presentes: ${ata.presentes}", style = MaterialTheme.typography.bodyMedium)
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = aoEditar) { Icon(Icons.Default.Edit, contentDescription = "Editar") }
                IconButton(onClick = aoExcluir) { Icon(Icons.Default.Delete, contentDescription = "Excluir") }
            }
        }
    }
}
