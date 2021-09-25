package com.hackyeon.tft_proto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackyeon.tft_proto.data.DataObject
import com.hackyeon.tft_proto.data.SummonerData
import com.hackyeon.tft_proto.databinding.ItemSummonerBinding

class SummonerRecyclerViewAdapter() :
    RecyclerView.Adapter<SummonerRecyclerViewAdapter.ViewHolder>() {
    var summonerList = mutableListOf<SummonerData>()

    class ViewHolder(val binding: ItemSummonerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(summoner: SummonerData){
            binding.summoner = summoner
            binding.viewModel = DataObject.viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSummonerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(summonerList[position])
    }

    override fun getItemCount(): Int = summonerList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}