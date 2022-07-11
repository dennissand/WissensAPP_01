package com.example.wissensapp_01.data

import android.util.Log
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.data.model.Card
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

object FirebaseService {
    private const val TAG = "FirebaseServiceBox"

    suspend fun getBoxData(): List<Box>? {
        try {
            val allBoxes = mutableListOf<Box>()
            val boxresults = FirebaseFirestore.getInstance().collection("boxes")
                .get()
                .await()
            for (document in boxresults.documents) {
                val box = document.toObject<Box>()
                if (box != null) {
                    allBoxes.add(box)
                }
            }
            return allBoxes
        } catch
        (e: Exception) {
            Log.e(TAG, "Error getting Boxs:$e")
            return null
        }
    }

    suspend fun deleteBox(box: Box): List<Box>? {
        val dbref = FirebaseFirestore.getInstance().collection("boxes")
        val boxQuery = dbref
            .whereEqualTo("boxId", box.boxId)
            .get()
            .await()
        if (boxQuery.documents.isNotEmpty()) {
            return try {
                val result = boxQuery.documents[0]
                dbref.document(result.id).delete()
                    .await()
                getBoxData()
            } catch (e: Exception) {
                Log.e(TAG, "No Box delet,$e")
                null
            }
        }
        return null
    }

    suspend fun getCardData(): List<Card>? {
        try {
            val allCards = mutableListOf<Card>()
            val cardresults = FirebaseFirestore.getInstance().collection("cards")
                .get()
                .await()
            for (document in cardresults.documents) {
                val card = document.toObject<Card>()
                if (card != null) {
                    allCards.add(card)
                }
            }
            return allCards
        } catch
        (e: Exception) {
            Log.e(FirebaseService.TAG, "Error getting Cards:$e")
            return null
        }
    }

    suspend fun deleteCard(card: Card): List<Card>? {
        val dbref = FirebaseFirestore.getInstance().collection("cards")
        val cardQuery = dbref
            .whereEqualTo("cardId", card.cardId)
            .get()
            .await()
        if (cardQuery.documents.isNotEmpty()) {
            return try {
                val result = cardQuery.documents[0]
                dbref.document(result.id).delete()
                    .await()
                getCardData()
            } catch (e: Exception) {
                Log.e(FirebaseService.TAG, "No Card deleted,$e")
                null
            }
        }
        return null
    }
}
