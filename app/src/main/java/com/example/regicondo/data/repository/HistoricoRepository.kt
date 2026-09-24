package com.example.regicondo.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.regicondo.data.model.HistoricoAprovacao
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class HistoricoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val colecao = db.collection("historico_aprovacoes")

    suspend fun criar(historico: HistoricoAprovacao): Result<String> = try {
        val doc = colecao.document()
        doc.set(historico.copy(id = doc.id)).await()
        Result.success(doc.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun observar(): Flow<List<HistoricoAprovacao>> = callbackFlow {
        val listener = colecao
            .orderBy("dataAprovacao", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, erro ->
                if (erro != null) { close(erro); return@addSnapshotListener }
                trySend(snapshot?.toObjects(HistoricoAprovacao::class.java) ?: emptyList())
            }
        awaitClose { listener.remove() }
    }

    suspend fun atualizar(historico: HistoricoAprovacao): Result<Unit> = try {
        colecao.document(historico.id).set(historico).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun excluir(id: String): Result<Unit> = try {
        colecao.document(id).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}