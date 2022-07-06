package com.example.wissensapp_01.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wissensapp_01.R
import com.example.wissensapp_01.data.model.Box

class BoxAdapter(
    private var boxNameList: ArrayList<Box>,
) : RecyclerView.Adapter<BoxAdapter.MyViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(List: List<Box>) {
        val item = boxNameList
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val boxName: TextView = itemView.findViewById(R.id.tV_box_name)
        val boxContent: TextView = itemView.findViewById(R.id.tV_box_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.box_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = boxNameList[position]
        holder.boxName.text = item.boxName
    }

    override fun getItemCount(): Int {

        return boxNameList.size
    }
}
