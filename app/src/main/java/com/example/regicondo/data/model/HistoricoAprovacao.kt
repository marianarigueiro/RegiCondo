package com.example.regicondo.data.model

data class HistoricoAprovacao(
    var id: String = "",
    val clausulaTitulo: String = "",
    val dataAprovacao: Long = System.currentTimeMillis(),
    val votosFavor: Int = 0,
    val votosContra: Int = 0,
    val votosAbstencao: Int = 0,
    val status: String = "Pendente" // Aprovado | Rejeitado | Pendente
)