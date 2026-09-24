package com.example.regicondo.data.model

data class ClausulaCondominial(
    var id: String = "",
    val titulo: String = "",
    val categoria: String = "",
    val descricao: String = "",
    val status: String = "Vigente", // Vigente | Em revisão | Revogada
    val versao: Int = 1,
    val dataAtualizacao: Long = System.currentTimeMillis()
)