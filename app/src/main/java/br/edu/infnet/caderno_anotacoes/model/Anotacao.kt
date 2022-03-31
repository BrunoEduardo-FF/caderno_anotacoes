package br.edu.infnet.caderno_anotacoes.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Anotacao(
    val userId: String = "",
    var titulo: String = "",
    var corpo: String = "",
    val data: Timestamp = Timestamp.now(),
    @DocumentId val id: String? = null
)
