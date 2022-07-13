package com.example.wissensapp_01.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.R
import com.example.wissensapp_01.databinding.FragmentCardAddBinding

class AddCardFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentCardAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            val a = binding.eTA.text.toString()
            val b = binding.eTB.text.toString()
            viewModel.saveCard(a, b)
            binding.eTA.setText("")
            binding.eTB.setText("")
        }



        val boxes = viewModel.boxes
        val boxnames = boxes.value?.map { it.boxName }?.toTypedArray()
        if (boxnames != null) {
            val spinnerAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, boxnames)
            binding.dropDownCard.adapter = spinnerAdapter
        }
    }
}
