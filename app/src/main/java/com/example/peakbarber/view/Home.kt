package com.example.peakbarber.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.peakbarber.R
import com.example.peakbarber.adapter.ServicosAdapter
import com.example.peakbarber.databinding.ActivityHomeBinding
import com.example.peakbarber.model.servicos

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val ListaSevicos: MutableList<servicos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome")

        binding.txtbemVindo.text = "Bem vindo(a)"
        val recyclerViewServicos = binding.recyclerViewServicos
        recyclerViewServicos.layoutManager = GridLayoutManager(this, 2)
        servicosAdapter = ServicosAdapter(this, ListaSevicos)
        recyclerViewServicos.setHasFixedSize(true)
        recyclerViewServicos.adapter = servicosAdapter
        getServicos()

        binding.btAgendar.setOnClickListener(){
            val intent = Intent(this, Agendamento::class.java)
            intent.putExtra("nome", nome)
            startActivity(intent)
        }
    }

    private fun getServicos() {

        val servico1 = servicos(R.drawable.img1, "Corte de cabelo")
        ListaSevicos.add(servico1)

        val servico2 = servicos(R.drawable.img2, "Corte de barba")
        ListaSevicos.add(servico2)

        val servico3 = servicos(R.drawable.img3, "Lavagem de cabelo")
        ListaSevicos.add(servico3)

        val servico4 = servicos(R.drawable.img4, "Tratamento de cabelo")
        ListaSevicos.add(servico4)
    }
}