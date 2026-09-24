package com.example.regicondo.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.regicondo.data.model.AtaAssembleia
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AtaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val colecao = db.collection("atas")

    suspend fun criar(ata: AtaAssembleia): Result<String> = try {
        val doc = colecao.document()
        doc.set(ata.copy(id = doc.id)).await()
        Result.success(doc.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun observar(): Flow<List<AtaAssembleia>> = callbackFlow {
        val listener = colecao
            .orderBy("data", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, erro ->
                if (erro != null) { close(erro); return@addSnapshotListener }
                trySend(snapshot?.toObjects(AtaAssembleia::class.java) ?: emptyList())
            }
        awaitClose { listener.remove() }
    }

    suspend fun atualizar(ata: AtaAssembleia): Result<Unit> = try {
        colecao.document(ata.id).set(ata).await()
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