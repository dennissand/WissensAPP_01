package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wissensapp_01.R
import com.example.wissensapp_01.adapter.BoxAdapter
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.databinding.FragmentBoxHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BoxFragment : Fragment() {

    private var _binding: FragmentBoxHomeBinding? = null
    private val dbref = Firebase.firestore.collection("cards")
    private val binding get() = _binding!!

    // private lateinit var boxRecyclerview: RecyclerView
    private lateinit var cardArrayList: ArrayList<Card>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val boxViewModel =
            ViewModelProvider(this).get(BoxViewModel::class.java)

        _binding = FragmentBoxHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardArrayList = arrayListOf<Card>()
        getAllCards()

        binding.btnBoxAdd.setOnClickListener {
            findNavController().navigate(R.id.addBoxFragment)
        }
    }

    private fun getAllCards() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val allCards = mutableListOf<Card>()
            val results = dbref
                .get()
                .await()
            for (document in results.documents) {
                val card = document.toObject<Card>()
                if (card != null) {
                    allCards.add(card)
                }
            }
            Log.e("result", allCards.toString())
            withContext(Dispatchers.Main) {
                binding.rwBoxHome.adapter = BoxAdapter(allCards as ArrayList<Card>)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    /*private fun getCardData() {

        dbref = FirebaseDatabase.getInstance().getReference("cards")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (cardSnapshot in snapshot.children) {

                        val card = cardSnapshot.getValue(Card::class.java)
                        cardArrayList.add(card!!)
                    }
                    Log.e("TAG", cardArrayList.toString())

                    binding.rwBoxHome.adapter = BoxAdapter(cardArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }*/
}
