package com.example.tugas5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas5.databinding.ItemPasienBinding

class PasienAdapter(private val listPasien: List<Pasien>) : RecyclerView.Adapter<PasienAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPasienBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPasienBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pasien = listPasien[position]
        holder.binding.tvNamaPasien.text = pasien.nama

        holder.binding.tvDetailPasien.text = "Lahir: ${pasien.tanggal_lahir} | Kelamin: ${pasien.jenis_kelamin} | Telp: ${pasien.no_telepon}"
        holder.binding.tvAlamat.text = "Alamat: ${pasien.alamat}"
    }

    override fun getItemCount(): Int = listPasien.size
}