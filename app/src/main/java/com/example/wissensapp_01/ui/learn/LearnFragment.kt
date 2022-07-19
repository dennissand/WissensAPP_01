package com.example.wissensapp_01.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.databinding.FragmentLearnHomeBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnHomeBinding? = null
    private val viewModel: MainViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: MainViewModel by activityViewModels()

        _binding = FragmentLearnHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnLearn.setOnClickListener {
            findNavController().navigate(LearnFragmentDirections.actionNavigationLearnHomeToChoiseLearnFragment())
        }

        binding.ibtnOk.setOnClickListener {
            findNavController().navigate(
                LearnFragmentDirections.actionNavigationLearnHomeToLearnDetailFragment(
                    true
                )
            )
        }

        binding.ibtnAgain.setOnClickListener {
            findNavController().navigate(
                LearnFragmentDirections.actionNavigationLearnHomeToLearnDetailFragment(
                    false
                )
            )
        }
    }
}
