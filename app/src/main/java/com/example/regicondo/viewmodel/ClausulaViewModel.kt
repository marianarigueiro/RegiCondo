package com.example.regicondo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regicondo.data.model.ClausulaCondominial
import com.example.regicondo.data.repository.ClausulaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClausulaViewModel : ViewModel() {

    private val repository = ClausulaRepository()

    private val _clausulas = MutableStateFlow<List<ClausulaCondominial>>(emptyList())
    val clausulas: StateFlow<List<ClausulaCondominial>> = _clausulas.asStateFlow()

    private val _carregando = MutableStateFlow(false)
    val carregando: StateFlow<Boolean> = _carregando.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observar().collect { lista -> _clausulas.value = lista }
        }
    }

    fun salvar(clausula: ClausulaCondominial, aoConcluir: (Boolean) -> Unit) {
        viewModelScope.launch {
            _carregando.value = true
            val resultado = if (clausula.id.isEmpty()) {
                repository.criar(clausula)
            } else {
                repository.atualizar(clausula)
            }
            _carregando.value = false
            aoConcluir(resultado.isSuccess)
        }
    }

    fun excluir(id: String) {
        viewModelScope.launch { repository.excluir(id) }
    }
}