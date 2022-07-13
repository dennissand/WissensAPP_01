package com.example.wissensapp_01.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.adapter.CardAdapter
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.databinding.FragmentCardHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CardFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val dbref = Firebase.firestore.collection("cards")
    private var _binding: FragmentCardHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reCardView = binding.rwCardEdit
        val adapter = CardAdapter(emptyList(), deleteCard)
        reCardView.adapter = adapter
        viewModel.cards.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitCardList(it)
                // Log.e("Observer", it.size.toString())
            }
        )

        binding.btnCardAdd.setOnClickListener {
            findNavController().navigate(CardFragmentDirections.actionNavigationCardHomeToAddCardFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val deleteCard = { card: Card ->
        viewModel.deleteCard(card)
    }
}
