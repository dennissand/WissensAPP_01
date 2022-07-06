package com.example.wissensapp_01.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.databinding.FragmentCardAddBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class AddCardFragment : Fragment() {

    private val cardCollectionRef = Firebase.firestore.collection("cards")

    private var _binding: FragmentCardAddBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cardViewModel =
            ViewModelProvider(this).get(CardViewModel::class.java)

        _binding = FragmentCardAddBinding.inflate(inflater, container, false)
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
            val card = createCard()
            saveCard(card)
        }
    }

    private fun createCard(): Card {
        val cardId = UUID.randomUUID().toString()
        val a = binding.eTA.text.toString()
        val b = binding.eTB.text.toString()
        val cardLearned = false
        val boxId = ""
        return Card(
            cardId = cardId,
            a = a,
            b = b,
            boxId = boxId,
            cardLearned = cardLearned
        )
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
                binding.eTA.setText("")
                binding.eTB.setText("")
                binding.eTBoxName.setText("")
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
}
