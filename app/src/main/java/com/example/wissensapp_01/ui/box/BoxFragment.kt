package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.R
import com.example.wissensapp_01.adapter.BoxAdapter
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.databinding.FragmentBoxHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BoxFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val dbref = Firebase.firestore.collection("boxes")
    private var _binding: FragmentBoxHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        val reBoxView = binding.rwBoxHome
        val adapter = BoxAdapter(emptyList(), deleteBox)
        reBoxView.adapter = adapter
        viewModel.boxes.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitBoxList(it)
                Log.e("Observer", it.size.toString())
            }
        )

        binding.btnBoxAdd.setOnClickListener {
            findNavController().navigate(R.id.addBoxFragment)
        }
    }

    private val deleteBox = { box: Box -> viewModel.deleteBox(box) }
}
