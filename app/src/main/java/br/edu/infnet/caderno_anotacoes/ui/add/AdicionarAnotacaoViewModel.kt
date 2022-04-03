package br.edu.infnet.caderno_anotacoes.ui.add

import androidx.lifecycle.ViewModel
import br.edu.infnet.caderno_anotacoes.database.dao.AnotacaoDao
import br.edu.infnet.caderno_anotacoes.model.Anotacao

class AdicionarAnotacaoViewModel : ViewModel() {


    fun adicionar(userId: String, titulo: String, corpo: String) {
        AnotacaoDao.insert(Anotacao(userId, titulo, corpo))
    }


}