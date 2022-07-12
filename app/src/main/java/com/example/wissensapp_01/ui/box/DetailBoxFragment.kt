package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.adapter.CardAdapter
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.databinding.FragmentBoxDetailBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailBoxFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val dbref = Firebase.firestore.collection("boxes")
    private var _binding: FragmentBoxDetailBinding? = null
    private val binding get() = _binding!!
    private var boxid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { boxid = it.getString("boxid").toString() }
        _binding = FragmentBoxDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBoxCards(boxid)
        val reDetailView = binding.rwBoxDetail
        val adapter = CardAdapter(emptyList(), deleteCard)
        reDetailView.adapter = adapter
        viewModel.boxcards.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitCardList(it)
                // Log.e("Observer", it.size.toString())
            }
        )

        /*binding.btnBoxAdd.setOnClickListener {
            findNavController().navigate(R.id.addBoxFragment)
        }*/
    }

    private val deleteCard = { card: Card ->
        viewModel.deleteCard(card)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
