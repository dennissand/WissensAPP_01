package com.example.wissensapp_01.ui.card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.databinding.FragmentCardHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class CardFragment : Fragment() {

    private val cardCollectionRef = Firebase.firestore.collection("cards")

    private var _binding: FragmentCardHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cardViewModel =
            ViewModelProvider(this).get(CardViewModel::class.java)

        _binding = FragmentCardHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val card = getOldCard()
            saveCard(card)
        }

        binding.btnEdit.setOnClickListener {
            getAllCards()
        }
    }

    private fun getOldCard(): Card {
        val cardId = UUID.randomUUID().toString()
        val a = binding.eTA.text.toString()
        val b = binding.eTB.text.toString()
        val boxName = "Englisch 1"
        val boxContent = "English"
        val boxColor = "purple"
        val cardLearned = false
        return Card(cardId, a, b, boxName, boxContent, boxColor, cardLearned)
    }

    private fun saveCard(card: Card) = CoroutineScope(Dispatchers.IO).launch {
        try {
            cardCollectionRef.add(card).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    "Successfully saved data.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteCard(card: Card) = CoroutineScope(Dispatchers.IO).launch {
        val cardQuery = cardCollectionRef
            .whereEqualTo("cardId", card.cardId)
            .get()
            .await()
        if (cardQuery.documents.isNotEmpty()) {
            try {
                val result = cardQuery.documents[0]
                cardCollectionRef.document(result.id).delete()
                    .await()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(), e.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(), "No card matched the query",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getAllCards() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val allCards = mutableListOf<Card>()
            val results = cardCollectionRef
                .get()
                .await()
            for (document in results.documents) {
                val card = document.toObject<Card>()
                if (card != null) {
                    allCards.add(card)
                }
            }
            Log.e("result", allCards.toString())
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
