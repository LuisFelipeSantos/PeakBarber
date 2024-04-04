package com.example.peakbarber

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.peakbarber.databinding.ActivityMainBinding
import com.example.peakbarber.view.Cadastro
import com.example.peakbarber.view.Home
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()
    var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btLogin.setOnClickListener{

            val email = binding.EditTextNome.text.toString()
            val senha = binding.EditTextSenha.text.toString()

            when{
                email.isEmpty() -> {
                    aviso(it,"Preencha o campo do email para prosseguir!")
                }
                senha.isEmpty() -> {
                    aviso(it,"Preencha o campo de senha para prosseguir!")
                }
                senha.length <= 3 -> {
                    aviso(it, "A sua senha deve conter pelo menos 4 caracteres")
                }
                else -> {
                    auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {autenticacao ->
                        if (autenticacao.isSuccessful){
                            userEmail = email
                            navegarPraHome(email)
                        }

                    }.addOnFailureListener {
                        val snackbar = Snackbar.make(binding.root, "Usuário ou senha incorretos", Snackbar.LENGTH_SHORT)
                        snackbar.show()


                    }
                }
            }
        }

        binding.textLinkCadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }


    }

    private fun aviso(view: View, aviso: String){
        val snackbar = Snackbar.make(view, aviso,Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun navegarPraHome(email: String) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("email", email)
        startActivity(intent)

    }

}
