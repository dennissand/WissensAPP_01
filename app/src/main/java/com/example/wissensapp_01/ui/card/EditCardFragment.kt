package com.example.wissensapp_01.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.databinding.FragmentCardEditBinding

class EditCardFragment : Fragment() {

    private var _binding: FragmentCardEditBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: MainViewModel by activityViewModels()

        _binding = FragmentCardEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}
