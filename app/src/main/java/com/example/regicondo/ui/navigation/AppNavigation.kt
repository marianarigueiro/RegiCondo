package com.example.regicondo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.regicondo.ui.screens.atas.FormAtaScreen
import com.example.regicondo.ui.screens.atas.ListaAtasScreen
import com.example.regicondo.ui.screens.clausulas.FormClausulaScreen
import com.example.regicondo.ui.screens.clausulas.ListaClausulasScreen
import com.example.regicondo.ui.screens.historico.FormHistoricoScreen
import com.example.regicondo.ui.screens.historico.ListaHistoricoScreen
import com.example.regicondo.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Tela.Home.rota) {

        composable(Tela.Home.rota) {
            HomeScreen(navController)
        }

        composable(Tela.ListaClausulas.rota) {
            ListaClausulasScreen(navController)
        }
        composable(
            route = Tela.FormClausula.rota,
            arguments = listOf(navArgument("clausulaId") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("clausulaId") ?: ""
            FormClausulaScreen(navController, id)
        }

        composable(Tela.ListaAtas.rota) {
            ListaAtasScreen(navController)
        }
        composable(
            route = Tela.FormAta.rota,
            arguments = listOf(navArgument("ataId") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("ataId") ?: ""
            FormAtaScreen(navController, id)
        }

        composable(Tela.ListaHistorico.rota) {
            ListaHistoricoScreen(navController)
        }
        composable(
            route = Tela.FormHistorico.rota,
            arguments = listOf(navArgument("historicoId") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("historicoId") ?: ""
            FormHistoricoScreen(navController, id)
        }
    }
}