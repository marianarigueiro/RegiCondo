package com.example.regicondo.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.regicondo.data.model.ClausulaCondominial
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ClausulaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val colecao = db.collection("clausulas")

    // CREATE
    suspend fun criar(clausula: ClausulaCondominial): Result<String> = try {
        val doc = colecao.document()
        doc.set(clausula.copy(id = doc.id)).await()
        Result.success(doc.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // READ (em tempo real — a lista se atualiza sozinha quando o Firestore muda)
    fun observar(): Flow<List<ClausulaCondominial>> = callbackFlow {
        val listener = colecao
            .orderBy("dataAtualizacao", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, erro ->
                if (erro != null) {
                    close(erro)
                    return@addSnapshotListener
                }
                trySend(snapshot?.toObjects(ClausulaCondominial::class.java) ?: emptyList())
            }
        awaitClose { listener.remove() }
    }

    // UPDATE
    suspend fun atualizar(clausula: ClausulaCondominial): Result<Unit> = try {
        colecao.document(clausula.id).set(clausula).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // DELETE
    suspend fun excluir(id: String): Result<Unit> = try {
        colecao.document(id).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}