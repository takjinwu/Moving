package com.example.moving.chw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moving.api.kakao.KakaoPlace
import com.example.moving.databinding.ItemCinemaRowBinding

class CinemaAdapter(private val items: List<KakaoPlace>) :
    RecyclerView.Adapter<CinemaAdapter.VH>() {

    inner class VH(val binding: ItemCinemaRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCinemaRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.txtName.text = item.place_name
        holder.binding.txtAddress.text = item.address_name
    }

    override fun getItemCount(): Int = items.size
}
