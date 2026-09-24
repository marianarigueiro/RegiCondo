package com.example.regicondo.viewmodel

// AtaViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regicondo.data.model.AtaAssembleia
import com.example.regicondo.data.repository.AtaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AtaViewModel : ViewModel() {

    private val repository = AtaRepository()

    private val _atas = MutableStateFlow<List<AtaAssembleia>>(emptyList())
    val atas: StateFlow<List<AtaAssembleia>> = _atas.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observar().collect { lista -> _atas.value = lista }
        }
    }

    fun salvar(ata: AtaAssembleia, aoConcluir: (Boolean) -> Unit) {
        viewModelScope.launch {
            val resultado = if (ata.id.isEmpty()) {
                repository.criar(ata)
            } else {
                repository.atualizar(ata)
            }
            aoConcluir(resultado.isSuccess)
        }
    }

    fun excluir(id: String) {
        viewModelScope.launch { repository.excluir(id) }
    }
}