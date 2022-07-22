package com.example.wissensapp_01.ui.learn

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        _binding = FragmentLearnHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boxes = viewModel.boxes
        val boxnames = boxes.value?.map { it.boxName }?.toTypedArray()
        var boxID = boxes.value?.get(0)?.boxId

        if (boxnames != null) {
            val spinnerAdapter =
                ArrayAdapter(requireContext(), R.layout.simple_spinner_item, boxnames)
            binding.spinnerLearnHomeBox.adapter = spinnerAdapter
            binding.spinnerLearnHomeBox.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    boxID = boxes.value!![position].boxId
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }

        binding.ibtnLearn.setOnClickListener {
            findNavController().navigate(
                LearnFragmentDirections.actionNavigationLearnHomeToLearnCardFragment(
                    boxID!!
                )
            )
        }

        binding.ibtnOk.setOnClickListener {
            findNavController().navigate(
                LearnFragmentDirections.actionNavigationLearnHomeToLearnDetailFragment(
                    true,
                    boxID!!
                )
            )
        }

        binding.ibtnAgain.setOnClickListener {
            findNavController().navigate(
                LearnFragmentDirections.actionNavigationLearnHomeToLearnDetailFragment(
                    false,
                    boxID!!
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
