package com.example.regicondo.ui.navigation

sealed class Tela(val rota: String) {
    object Home : Tela("home")

    object ListaClausulas : Tela("lista_clausulas")
    object FormClausula : Tela("form_clausula?clausulaId={clausulaId}") {
        fun criarRota(clausulaId: String = "") = "form_clausula?clausulaId=$clausulaId"
    }

    object ListaAtas : Tela("lista_atas")
    object FormAta : Tela("form_ata?ataId={ataId}") {
        fun criarRota(ataId: String = "") = "form_ata?ataId=$ataId"
    }

    object ListaHistorico : Tela("lista_historico")
    object FormHistorico : Tela("form_historico?historicoId={historicoId}") {
        fun criarRota(historicoId: String = "") = "form_historico?historicoId=$historicoId"
    }
}