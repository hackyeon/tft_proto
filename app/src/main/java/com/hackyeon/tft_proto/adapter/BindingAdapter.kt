package com.hackyeon.tft_proto.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hackyeon.tft_proto.data.SummonerData

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, id: String?){
        view.visibility = if(id == null) View.INVISIBLE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("imageGlide")
    fun imgGlide(view: AppCompatImageView, url: Int?){
        Glide.with(view.context)
            .load("http://ddragon.leagueoflegends.com/cdn/11.19.1/img/profileicon/${url}.png")
            .centerCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("notifyDataSetChanged")
    fun setChange(recyclerView: RecyclerView, items: MutableList<SummonerData>){
        if(recyclerView.adapter == null) {
            var adapter = SummonerRecyclerViewAdapter()
            adapter.setHasStableIds(true)
            recyclerView.adapter = adapter
        }

        var summonerRecyclerViewAdapter = recyclerView.adapter as SummonerRecyclerViewAdapter
        summonerRecyclerViewAdapter.summonerList = items

        summonerRecyclerViewAdapter.notifyDataSetChanged()
    }
}