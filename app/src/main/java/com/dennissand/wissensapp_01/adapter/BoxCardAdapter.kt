package com.dennissand.wissensapp_01.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dennissand.wissensapp_01.R
import com.dennissand.wissensapp_01.data.model.Card

class BoxCardAdapter(
    private var boxcardIdList: List<Card>

) : RecyclerView.Adapter<BoxCardAdapter.BoxCardViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitBoxCardList(list: List<Card>) {
        boxcardIdList = list
        notifyDataSetChanged()
    }

    class BoxCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val a: TextView = itemView.findViewById(R.id.tV_boxcard_a)
        val b: TextView = itemView.findViewById(R.id.tV_boxcard_b)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_list_item,
            parent,
            false
        )
        return BoxCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BoxCardViewHolder, position: Int) {
        val item = boxcardIdList[position]
        holder.a.text = item.a
        holder.b.text = item.b
    }

    override fun getItemCount(): Int {
        return boxcardIdList.size
    }
}
