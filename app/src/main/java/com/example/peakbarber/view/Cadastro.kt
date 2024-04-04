package com.example.peakbarber.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.peakbarber.databinding.CadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class Cadastro : AppCompatActivity() {

    private lateinit var binding: CadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btCadastro.setOnClickListener {

            val email = binding.EditTextEmail.text.toString()
            val senha = binding.EditTextSenha.text.toString()

            when {
                email.isEmpty() -> {
                    aviso(it, "Preencha o campo do nome para prosseguir!")
                }
                senha.isEmpty() -> {
                    aviso(it, "Preencha o campo de senha para prosseguir!")
                }
                senha.length <= 3 -> {
                    aviso(it, "A sua senha deve conter pelo menos 4 caracteres")
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
                        if (cadastro.isSuccessful){
                            val snackbar = Snackbar.make(it, "Cadastrado Com Sucesso!", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.parseColor("#FF018786"))
                            snackbar.setTextColor(Color.parseColor("#FFFFFF"))
                            snackbar.show()
                            binding.EditTextEmail.setText("")
                            binding.EditTextSenha.setText("")
                    }
                    }.addOnFailureListener {

                    }
                    Handler().postDelayed({
                        finish()
                    }, 1600)
                }
            }
        }

        binding.textLinkLogin.setOnClickListener {
            finish()
        }
    }

    private fun aviso(view: View, aviso: String) {
        val snackbar = Snackbar.make(view, aviso, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }
}
