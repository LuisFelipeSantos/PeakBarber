package com.example.peakbarber.view

import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.peakbarber.databinding.ActivityAgendamentoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Agendamento : AppCompatActivity() {

    private lateinit var binding: ActivityAgendamentoBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!

        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome").toString()

        val datePicker = binding.datepicker
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var dia = dayOfMonth.toString()
            var mes = ""

            if (dayOfMonth < 10) {
                dia = "0$dayOfMonth"
            }
            if (monthOfYear < 10) {
                mes = "0${monthOfYear + 1}"
            } else {
                mes = (monthOfYear + 1).toString()
            }
            data = "$dia / $mes / $year"
        }

        binding.timepicker.setOnTimeChangedListener { _, hourOfDay, minute ->

            val minuto: String

            if (minute < 10) {
                minuto = "0$minute"
            } else {
                minuto = minute.toString()
            }
            hora = "$hourOfDay:$minuto"
        }
        binding.timepicker.setIs24HourView(true)

        binding.btAgendar2.setOnClickListener {

            val barbeiro1 = binding.barbeiro1
            val barbeiro2 = binding.barbeiro2
            val barbeiro3 = binding.barbeiro3


            when {
                hora.isEmpty() -> {
                    aviso(it, "Preencha o horário!", "#FF0000")
                }

                hora < "8:00" && hora > "19:00" -> {
                    aviso(it, "PeakBarber está fechado - horário de atendimento das 08:00 as 19:00!", "#FF0000")
                }

                data.isEmpty() -> {
                    aviso(it, "Coloque uma data!", "#FF0000")
                }

                barbeiro1.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    currentUser?.email?.let { email ->
                        salvarAgendamento(it, email, "Caio", data, hora)
                    } ?: aviso(it, "Usuário não autenticado", "#FF0000")
                }

                barbeiro2.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    currentUser?.email?.let { email ->
                        salvarAgendamento(it, email, "Felipe", data, hora)
                    } ?: aviso(it, "Usuário não autenticado", "#FF0000")
                }

                barbeiro3.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    currentUser?.email?.let { email ->
                        salvarAgendamento(it, email, "Fernando", data, hora)
                    } ?: aviso(it, "Usuário não autenticado", "#FF0000")
                }

                else -> {
                    aviso(it, "Escolha um barbeiro!", "#FF0000")
                }
            }
        }

    }

    private fun aviso(view: View, aviso: String, cor: String) {
        val snackbar = Snackbar.make(view, aviso, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()

    }

    private fun salvarAgendamento(view: View, cliente: String, barbeiro: String, data: String, hora: String) {

        val db = FirebaseFirestore.getInstance()

        val dadosUsuario = hashMapOf(
            "cliente" to cliente,
            "barbeiro" to barbeiro,
            "data" to data,
            "hora" to hora
        )

        db.collection("agendamento").document(cliente).set(dadosUsuario).addOnCompleteListener {
            aviso(view,"Agendamento Realizado com Sucesso!", "#FF03DAC5")
        }.addOnFailureListener {
            aviso(view,"Erro no Servidor", "#FF0000")
        }
        }
    }