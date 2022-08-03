package com.example.wissensapp_01.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wissensapp_01.R
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.ui.box.BoxFragmentDirections

class BoxAdapter(
    private var boxNameList: List<Box>,
    private var deleteBox: (box: Box) -> Unit,
    private var navtoDetail: (id: String) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<BoxAdapter.BoxViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitBoxList(list: List<Box>?) {
        if (list != null) {
            boxNameList = list
        }
        notifyDataSetChanged()
    }

    class BoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val boxName: TextView = itemView.findViewById(R.id.tV_box_name)
        val boxContent: TextView = itemView.findViewById(R.id.tV_box_content)
        val boxDelet: ImageButton = itemView.findViewById(R.id.ibtn_delete_box)
        val boxEdit: ImageButton = itemView.findViewById(R.id.ibtn_edit_box)
        val boxItem: ImageView = itemView.findViewById(R.id.iV_item_box)
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
        val boxid: String = item.boxId
        holder.boxName.text = item.boxName
        holder.boxContent.text = item.boxContent

        holder.boxDelet.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Löschen ?")
                .setMessage("Möchtest Du die Box wirklich löschen ?")
                .setPositiveButton(
                    "Ja",
                    DialogInterface.OnClickListener { dialogInterface, i -> deleteBox(item) }
                )
                .setNegativeButton(
                    "Nein",
                    DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int -> }
                )
                .create()
                .show()
        }

        holder.boxItem.setOnClickListener {
            navtoDetail(boxid)
        }

        holder.boxEdit.setOnClickListener {
            holder.itemView.findNavController()
                .navigate(BoxFragmentDirections.actionBoxFragmentToEditBoxFragment(boxid))
        }
    }

    override fun getItemCount(): Int {
        return boxNameList.size
    }
}
