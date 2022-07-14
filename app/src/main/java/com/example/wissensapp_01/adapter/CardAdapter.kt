package com.example.wissensapp_01.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wissensapp_01.R
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.ui.card.CardFragmentDirections

class CardAdapter(
    private var cardIdList: List<Card>,
    private var deleteCard: (card: Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitCardList(list: List<Card>?) {
        if (list != null) {
            cardIdList = list
        }
        notifyDataSetChanged()
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val a: TextView = itemView.findViewById(R.id.tV_card_a)
        val b: TextView = itemView.findViewById(R.id.tV_card_b)
        val cardDelet: ImageButton = itemView.findViewById(R.id.ibtn_delete_card)
        val cardEdit: ImageButton = itemView.findViewById(R.id.ibtn_edit_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent,
            false
        )
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = cardIdList[position]
        holder.a.text = item.a
        holder.b.text = item.b
        holder.cardDelet.setOnClickListener {
            deleteCard(item)
        }
        holder.cardEdit.setOnClickListener{
            holder.itemView.findNavController()
                .navigate(CardFragmentDirections.actionNavigationCardHomeToEditCardFragment(item.cardId))
        }
    }

    override fun getItemCount(): Int {
        return cardIdList.size
    }
}
