package com.example.regicondo.data.model

data class AtaAssembleia(
    var id: String = "",
    val titulo: String = "",
    val tipo: String = "Ordinária", // Ordinária | Extraordinária
    val data: Long = System.currentTimeMillis(),
    val pauta: String = "",
    val decisoes: String = "",
    val presentes: Int = 0
)