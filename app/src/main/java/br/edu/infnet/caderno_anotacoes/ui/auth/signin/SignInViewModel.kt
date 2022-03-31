package br.edu.infnet.caderno_anotacoes.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInViewModel : ViewModel() {
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    init {
        _msg.value = ""
        _status.value = false

    }

    fun signIn(email: String, pass: String) {
        Firebase
            .auth
            .signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                if (it.user != null) {
                    _status.value = true
                }
            }
            .addOnFailureListener {
                _msg.value = it.message
            }
    }


}