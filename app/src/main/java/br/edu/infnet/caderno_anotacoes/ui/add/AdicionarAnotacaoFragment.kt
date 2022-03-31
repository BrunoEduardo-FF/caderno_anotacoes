package br.edu.infnet.caderno_anotacoes.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.infnet.caderno_anotacoes.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AdicionarAnotacaoFragment : Fragment() {

    private lateinit var viewModel: AdicionarAnotacaoViewModel
    private lateinit var lblTitulo: EditText
    private lateinit var lblCorpo: EditText
    private lateinit var btnAdicionar: Button
    private lateinit var userId: String
    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_adicionar_anotacao, container, false)
        setupUser()
        setupWidgets(view)
        viewModel = ViewModelProvider(this).get(AdicionarAnotacaoViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdicionar.setOnClickListener {
            if(!haCampoVazio()) {
                viewModel.adicionar(userId, lblTitulo.text.toString(), lblCorpo.text.toString())
                findNavController().popBackStack()
            }
        }

    }

    private fun setupWidgets(view: View) {
        lblTitulo = view.findViewById(R.id.fragment_adicionar_anotacao_txt_titulo)
        lblCorpo = view.findViewById(R.id.fragment_adicionar_anotacao_txt_corpo)
        btnAdicionar = view.findViewById(R.id.fragment_adicionar_anotacao_btn_adicionar)
    }

    private fun haCampoVazio():Boolean {
        if(lblTitulo.text.isNullOrBlank()) return true
        if(lblCorpo.text.isNullOrBlank()) return true
        return false
    }

    private fun setupUser() {
        user = Firebase.auth.currentUser
        userId = user?.uid.toString()
    }

}