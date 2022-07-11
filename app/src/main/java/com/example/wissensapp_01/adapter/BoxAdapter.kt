package com.example.wissensapp_01.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wissensapp_01.R
import com.example.wissensapp_01.data.model.Box

class BoxAdapter(
    private var boxNameList: List<Box>,
    private var deleteBox: (box: Box) -> Unit
) : RecyclerView.Adapter<BoxAdapter.BoxViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitBoxList(list: List<Box>) {
        boxNameList = list
        notifyDataSetChanged()
    }

    class BoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val boxName: TextView = itemView.findViewById(R.id.tV_box_name)
        val boxContent: TextView = itemView.findViewById(R.id.tV_box_content)
        val boxDelet: ImageButton = itemView.findViewById(R.id.ibtn_delete_box)
        val boxEdit: ImageButton = itemView.findViewById(R.id.ibtn_edit_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.box_item,
            parent,
            false
        )
        return BoxViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {
        val item = boxNameList[position]
        holder.boxName.text = item.boxName
        holder.boxContent.text = item.boxContent
        holder.boxDelet.setOnClickListener {
            deleteBox(item)
        }
    }

    override fun getItemCount(): Int {
        return boxNameList.size
    }
}
