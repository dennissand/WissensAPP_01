package com.dennissand.wissensapp_01.ui.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dennissand.wissensapp_01.MainViewModel
import com.dennissand.wissensapp_01.databinding.FragmentBoxAddBinding

class AddBoxFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentBoxAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoxAddBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnSaveBox.setOnClickListener {
            val boxName = binding.eTBoxName.text.toString()
            val boxContent = binding.eTBoxContent.text.toString()
            viewModel.saveBox(boxName, boxContent, requireContext())
            binding.eTBoxName.setText("")
            binding.eTBoxContent.setText("")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
