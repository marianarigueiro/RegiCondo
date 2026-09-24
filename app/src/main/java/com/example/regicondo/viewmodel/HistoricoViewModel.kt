package com.example.regicondo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regicondo.data.model.HistoricoAprovacao
import com.example.regicondo.data.repository.HistoricoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoricoViewModel : ViewModel() {

    private val repository = HistoricoRepository()

    private val _historico = MutableStateFlow<List<HistoricoAprovacao>>(emptyList())
    val historico: StateFlow<List<HistoricoAprovacao>> = _historico.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observar().collect { lista -> _historico.value = lista }
        }
    }

    fun salvar(item: HistoricoAprovacao, aoConcluir: (Boolean) -> Unit) {
        viewModelScope.launch {
            val resultado = if (item.id.isEmpty()) {
                repository.criar(item)
            } else {
                repository.atualizar(item)
            }
            aoConcluir(resultado.isSuccess)
        }
    }

    fun excluir(id: String) {
        viewModelScope.launch { repository.excluir(id) }
    }
}