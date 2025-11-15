package com.example.moving.chw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moving.api.kobis.MovieBoxOffice
import com.example.moving.databinding.ItemMovieChartBinding

class MovieChartAdapter(private val items: List<MovieBoxOffice>) :
    RecyclerView.Adapter<MovieChartAdapter.VH>() {

    inner class VH(val binding: ItemMovieChartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMovieChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.txtRank.text = item.rank
        holder.binding.txtTitle.text = item.movieNm
        holder.binding.txtAudience.text = "누적관객수 : ${item.audiAcc}명"
    }

    override fun getItemCount(): Int = items.size
}