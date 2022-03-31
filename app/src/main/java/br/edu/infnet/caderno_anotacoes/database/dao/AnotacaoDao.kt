package br.edu.infnet.caderno_anotacoes.database.dao

import br.edu.infnet.caderno_anotacoes.model.Anotacao
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object AnotacaoDao {
    val collection = Firebase.firestore.collection("anotacao")

    fun insert(anotacao: Anotacao): Task<DocumentReference> {
        return collection.add(anotacao)
    }

    fun update(anotacao: Anotacao):Task<Void> {
        return collection.document(anotacao!!.id!!).set(anotacao)
    }

    fun delete(anotacao: Anotacao?): Task<Void> {
        return collection.document(anotacao?.id!!).delete()
    }

    fun read(docId: String):Task<DocumentSnapshot> {
        return collection.document(docId).get()
    }

    fun listUserDocs(userId: String): Task<QuerySnapshot> {
        return collection
            .whereEqualTo("userId", userId)
            .get()
    }

}