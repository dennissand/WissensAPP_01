package com.example.wissensapp_01.data

import android.util.Log
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.data.model.Card
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.*

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
            Log.e(TAG, "Error getting Cards:$e")
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
                Log.e(TAG, "No Card deleted,$e")
                null
            }
        }
        return null
    }

    private fun createCard(a: String, b: String, boxId: String): Card {
        val cardId = UUID.randomUUID().toString()
        val cardLearned = false
        return Card(
            cardId = cardId,
            a = a,
            b = b,
            boxId = boxId,
            cardLearned = cardLearned
        )
    }

    suspend fun saveCard(a: String, b: String, boxID: String): List<Card>? {
        val dbref = FirebaseFirestore.getInstance().collection("cards")
        val card = createCard(a, b, boxID)
        return try {
            dbref.add(card)
                .await()
            getCardData()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving Card:$e")
            null
        }
    }

    private fun createBox(boxName: String, boxContent: String): Box {
        val boxId = UUID.randomUUID().toString()
        return Box(
            boxId = boxId,
            boxName = boxName,
            boxContent = boxContent
        )
    }

    suspend fun saveBox(boxName: String, boxContent: String): List<Box>? {
        val dbref = FirebaseFirestore.getInstance().collection("boxes")
        val box = createBox(boxName, boxContent)
        return try {
            dbref.add(box)
                .await()
            getBoxData()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving Box:$e")
            null
        }
    }

    suspend fun updateCard(card: Card): List<Card>? {
        val dbref = FirebaseFirestore.getInstance().collection("cards")
        val cardQuery = dbref
            .whereEqualTo("cardId", card.cardId)
            .get()
            .await()
        if (cardQuery.documents.isNotEmpty()) {
            return try {
                val result = cardQuery.documents[0]
                dbref.document(result.id).update("a", card.a, "b", card.b, "boxId", card.boxId)
                    .await()
                getCardData()
            } catch (e: Exception) {
                Log.e(TAG, "No Card update,$e")
                null
            }
        }
        return null
    }

    suspend fun updateBox(box: Box): List<Box>? {
        val dbref = FirebaseFirestore.getInstance().collection("boxes")
        val boxQuery = dbref
            .whereEqualTo("boxId", box.boxId)
            .get()
            .await()
        if (boxQuery.documents.isNotEmpty()) {
            return try {
                val result = boxQuery.documents[0]
                dbref.document(result.id).update("boxName", box.boxName, "boxContent", box.boxContent)
                    .await()
                getBoxData()
            } catch (e: Exception) {
                Log.e(FirebaseService.TAG, "No Box Update ,$e")
                null
            }
        }
        return null
    }
}
