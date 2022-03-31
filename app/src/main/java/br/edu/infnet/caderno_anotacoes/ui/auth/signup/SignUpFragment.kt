package br.edu.infnet.caderno_anotacoes.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.edu.infnet.caderno_anotacoes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private lateinit var txtEmail: EditText
    private lateinit var txtSenha: EditText
    private lateinit var txtRepSenha: EditText
    private lateinit var btnCadastrar: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        auth = Firebase.auth

        txtEmail = view.findViewById(R.id.fragment_sign_up_txt_email)
        txtSenha = view.findViewById(R.id.fragment_sign_up_txt_senha)
        txtRepSenha = view.findViewById(R.id.fragment_sign_up_txt_repetir_senha)
        btnCadastrar = view.findViewById(R.id.fragment_sign_up_btn_cadastrar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCadastrar.setOnClickListener {
            var email = txtEmail.text.toString()
            var senha = txtSenha.text.toString()
            var repSenha = txtRepSenha.text.toString()

            if (senha.compareTo(repSenha) == 0 && email.isNotBlank()) {
                var task = auth.createUserWithEmailAndPassword(email, senha)
                task.addOnCompleteListener {
                    if (it.isSuccessful && it.result != null) {
                        findNavController().popBackStack()
                        makeToast("Usuário ${it.result?.user.toString()} criado com sucesso.")
                    } else {
                        makeToast(it.exception?.message.toString())
                    }
                }
            } else {
                makeToast( "Senha não confere")
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}