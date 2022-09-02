package com.dennissand.wissensapp_01.adapter

import android.animation.AnimatorSet
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dennissand.wissensapp_01.R
import com.dennissand.wissensapp_01.data.model.Card

class LearnDetailAdapter(
    private var learncardIdList: List<Card>,
    private var front_anim: AnimatorSet,
    private var back_anim: AnimatorSet,
    private var scale: Float,
    private var cardtoggeld: (card: Card, cardLearned: Boolean) -> Unit,
    private var context: Context

) : RecyclerView.Adapter<LearnDetailAdapter.LearnDetailViewHolder>() {

    class LearnDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val a: TextView = itemView.findViewById(R.id.a_text)
        val b: TextView = itemView.findViewById(R.id.b_text)
        val cvFront: CardView = itemView.findViewById(R.id.learn_card_front)
        val cvBack: CardView = itemView.findViewById(R.id.learn_card_back)
        var again: ImageButton = itemView.findViewById(R.id.ibtn_again)
        var ok: ImageButton = itemView.findViewById(R.id.ibtn_ok)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnDetailViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_learn_item,
            parent,
            false
        )
        return LearnDetailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LearnDetailViewHolder, position: Int) {
        val item = learncardIdList[position]
        var isFront = true

        holder.a.text = item.a
        holder.b.text = item.b
        holder.cvFront.cameraDistance = 7000 * scale
        holder.cvBack.cameraDistance = 7000 * scale

        holder.cvBack.alpha = 0F

        holder.a.setOnClickListener {
            if (isFront) {
                front_anim.setTarget(holder.cvFront)
                back_anim.setTarget(holder.cvBack)
                front_anim.start()
                back_anim.start()
                isFront = false
            } else {
                front_anim.setTarget(holder.cvBack)
                back_anim.setTarget(holder.cvFront)
                back_anim.start()
                front_anim.start()
                isFront = true
            }
        }

        holder.again.setOnClickListener {
            cardtoggeld(item, false)
            Toast.makeText(context, context.resources.getString(R.string.ok_card), Toast.LENGTH_SHORT).show()
        }

        holder.ok.setOnClickListener {
            cardtoggeld(item, true)
            Toast.makeText(context, context.resources.getString(R.string.again_card), Toast.LENGTH_SHORT).show()
        }

        if (item.cardLearned) {
            holder.ok.visibility = View.GONE
            holder.again.visibility = View.VISIBLE
        } else {
            holder.again.visibility = View.GONE
            holder.ok.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return learncardIdList.size
    }
}