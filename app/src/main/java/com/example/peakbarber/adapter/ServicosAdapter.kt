package com.example.peakbarber.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.peakbarber.databinding.ServicosBinding
import com.example.peakbarber.model.servicos

class ServicosAdapter(private val context: Context, private val ListaServicos: MutableList<servicos>):
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        val itemLista = ServicosBinding.inflate(LayoutInflater.from(context), parent, false)
        return ServicosViewHolder(itemLista)
    }

    override fun getItemCount() = ListaServicos.size

    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        holder.imgServico.setImageResource(ListaServicos[position].img!!)
        holder.txtServico.text = ListaServicos[position].nome
    }

    inner class ServicosViewHolder(binding: ServicosBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgServico = binding.ImgServico
        val txtServico = binding.txtServico
    }
}