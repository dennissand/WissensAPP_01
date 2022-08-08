package com.example.wissensapp_01.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.wissensapp_01.R
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.data.model.Card
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.*

object FirebaseService {
    private const val TAG = "FirebaseServiceBox"

    suspend fun getBoxData(uid: String): List<Box>? {
        try {
            val allBoxes = mutableListOf<Box>()
            val boxresults = FirebaseFirestore.getInstance().collection("boxes")
                .get()
                .await()
            for (document in boxresults.documents) {
                val box = document.toObject<Box>()
                if (box != null) {
                    if (box.uid == uid) {
                        allBoxes.add(box)
                    }
                }
            }
            return allBoxes
        } catch
        (e: Exception) {
            Log.e(TAG, "Fehler beim laden der Boxen:$e")
            return null
        }
    }

    suspend fun deleteBox(box: Box, context: Context): List<Box>? {
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
                Toast.makeText(context, "Box gelöscht !", Toast.LENGTH_LONG).show()
                getBoxData(box.uid)
            } catch (e: Exception) {
                Log.e(TAG, "keine Box gelöscht,$e")
                Toast.makeText(context, "Box nicht gelöscht !", Toast.LENGTH_LONG).show()
                null
            }
        }
        return null
    }

    suspend fun getCardData(uid: String): List<Card>? {
        try {
            val allCards = mutableListOf<Card>()
            val cardresults = FirebaseFirestore.getInstance().collection("cards")
                .get()
                .await()
            for (document in cardresults.documents) {
                val card = document.toObject<Card>()
                if (card != null) {
                    if (card.uid == uid) {
                        allCards.add(card)
                    }
                }
            }
            return allCards
        } catch
        (e: Exception) {
            Log.e(TAG, "Fehler beim Laden der Karten:$e")
            return null
        }
    }

    suspend fun deleteCard(card: Card, context: Context): List<Card>? {
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
                Toast.makeText(context, "Karte gelöscht !", Toast.LENGTH_LONG).show()
                getCardData(card.uid)
            } catch (e: Exception) {
                Log.e(TAG, "Keine Karte gelöscht,$e")
                Toast.makeText(context, "Karte nicht gelöscht !", Toast.LENGTH_LONG).show()
                null
            }
        }
        return null
    }

    private fun createCard(
        a: String,
        b: String,
        boxId: String,
        cardLearned: Boolean,
        uid: String
    ): Card {
        val cardId = UUID.randomUUID().toString()
        return Card(
            cardId = cardId,
            a = a,
            b = b,
            boxId = boxId,
            cardLearned = cardLearned,
            uid = uid
        )
    }

    suspend fun saveCard(
        a: String,
        b: String,
        boxID: String,
        cardLearned: Boolean,
        context: Context,
        uid: String
    ): List<Card>? {
        val dbref = FirebaseFirestore.getInstance().collection("cards")
        val card = createCard(a, b, boxID, cardLearned, uid)
        return try {
            dbref.add(card)
                .await()
            Toast.makeText(
                context,
                context.resources.getString(R.string.save_Card),
                Toast.LENGTH_LONG
            ).show()
            getCardData(uid)
        } catch (e: Exception) {
            Log.e(TAG, "Fehler beim speichern der Karte:$e")
            Toast.makeText(context, "Karte nicht gespeichert !", Toast.LENGTH_LONG).show()
            null
        }
    }

    private fun createBox(boxName: String, boxContent: String, uid: String): Box {
        val boxId = UUID.randomUUID().toString()
        return Box(
            boxId = boxId,
            boxName = boxName,
            boxContent = boxContent,
            uid = uid
        )
    }

    suspend fun saveBox(
        boxName: String,
        boxContent: String,
        context: Context,
        uid: String
    ): List<Box>? {
        val dbref = FirebaseFirestore.getInstance().collection("boxes")
        val box = createBox(boxName, boxContent, uid)
        return try {
            dbref.add(box)
                .await()
            Toast.makeText(context, "Box gespeichert !", Toast.LENGTH_LONG).show()
            getBoxData(uid)
        } catch (e: Exception) {
            Log.e(TAG, "Fehler beim speichern der Box:$e")
            Toast.makeText(context, "Box nicht gespeichert !", Toast.LENGTH_LONG).show()
            null
        }
    }

    suspend fun updateCard(card: Card, context: Context): List<Card>? {
        val dbref = FirebaseFirestore.getInstance().collection("cards")
        val cardQuery = dbref
            .whereEqualTo("cardId", card.cardId)
            .get()
            .await()
        if (cardQuery.documents.isNotEmpty()) {
            return try {
                val result = cardQuery.documents[0]
                dbref.document(result.id)
                    .update(
                        "a",
                        card.a,
                        "b",
                        card.b,
                        "boxId",
                        card.boxId,
                        "cardLearned",
                        card.cardLearned

                    )
                    .await()
                Toast.makeText(context, "Karte geändert & gespeichert !", Toast.LENGTH_LONG)
                    .show()
                getCardData(card.uid)
            } catch (e: Exception) {
                Log.e(TAG, "Fehler beim ändern der Karte,$e")
                Toast.makeText(
                    context,
                    "Karte nicht geändert & gespeichert !",
                    Toast.LENGTH_LONG
                )
                    .show()
                null
            }
        }
        return null
    }

    suspend fun updateBox(box: Box, context: Context): List<Box>? {
        val dbref = FirebaseFirestore.getInstance().collection("boxes")
        val boxQuery = dbref
            .whereEqualTo("boxId", box.boxId)
            .get()
            .await()
        if (boxQuery.documents.isNotEmpty()) {
            return try {
                val result = boxQuery.documents[0]
                dbref.document(result.id)
                    .update("boxName", box.boxName, "boxContent", box.boxContent)
                    .await()
                Toast.makeText(context, "Box nicht geändert & gespeichert !", Toast.LENGTH_LONG)
                    .show()
                getBoxData(box.uid)
            } catch (e: Exception) {
                Log.e(TAG, "Fehler beim ändern der Box ,$e")
                Toast.makeText(context, "Box nicht geändert & gespeichert !", Toast.LENGTH_LONG)
                    .show()
                null
            }
        }
        return null
    }
}
