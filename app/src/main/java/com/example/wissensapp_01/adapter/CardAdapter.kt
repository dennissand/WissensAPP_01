package com.example.wissensapp_01.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wissensapp_01.R
import com.example.wissensapp_01.data.model.Card

class CardAdapter(
    private val cardIdList: ArrayList<Card>,
) : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(List: List<Card>) {
        val item = cardIdList
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val a: TextView = itemView.findViewById(R.id.tV_card_a)
        val b: TextView = itemView.findViewById(R.id.tV_card_b)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = cardIdList[position]
        holder.a.text = item.a
        holder.b.text = item.b
    }

    override fun getItemCount(): Int {
        return cardIdList.size
    }
}
