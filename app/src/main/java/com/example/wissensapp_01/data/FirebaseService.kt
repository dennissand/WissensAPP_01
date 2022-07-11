package com.example.wissensapp_01.data

import android.util.Log
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.data.model.Card
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object FirebaseService {
    private const val TAG = "FirebaseService"

    suspend fun getBoxData(): List<Box>? {
        try {
            val allBoxes = mutableListOf<Box>()
            val results = FirebaseFirestore.getInstance().collection("boxes")
                .get()
                .await()
            for (document in results.documents) {
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

    suspend fun deleteBox(box: Box) = CoroutineScope(Dispatchers.IO).launch {
        val dbref = FirebaseFirestore.getInstance().collection("boxes")
        val boxQuery = dbref
            .whereEqualTo("boxId", box.boxId)
            .get()
            .await()
        if (boxQuery.documents.isNotEmpty()) {
            try {
                val result = boxQuery.documents[0]
                dbref.document(result.id).delete()
                    .await()
            } catch (e: Exception) {
                Log.e(TAG, "No Box delet,$e")
            }
        }
    }

    suspend fun getCardData(cardId: String): Card? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("cards")
                .document(cardId).get().await().toObject<Card>()
        } catch
        (e: Exception) {
            Log.e(TAG, "Error getting Box details:$e")
            return null
        }
    }
}
