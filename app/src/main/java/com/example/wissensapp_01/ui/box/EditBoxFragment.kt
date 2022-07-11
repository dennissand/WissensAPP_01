package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.databinding.FragmentBoxEditBinding

class EditBoxFragment : Fragment() {

    private var _binding: FragmentBoxEditBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: MainViewModel by activityViewModels()

        _binding = FragmentBoxEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
