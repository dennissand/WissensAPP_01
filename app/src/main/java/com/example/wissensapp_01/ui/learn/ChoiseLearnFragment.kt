package com.example.wissensapp_01.ui.learn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.TAG
import com.example.wissensapp_01.databinding.FragmentLearnChoiseBinding

class ChoiseLearnFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentLearnChoiseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnChoiseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var boxID = ""

        val boxes = viewModel.boxes
        val boxnames = boxes.value?.map { it.boxName }?.toTypedArray()

        Log.e(TAG, boxnames.toString())

        if (boxnames != null) {
            val spinnerAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, boxnames)
            binding.spinnerLearnChoiceBox.adapter = spinnerAdapter
            binding.spinnerLearnChoiceBox.onItemSelectedListener = object :
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
        binding.ibtnStartLearn.setOnClickListener {
            findNavController().navigate(
                ChoiseLearnFragmentDirections.actionChoiseLearnFragmentToLearnCardFragment(
                    boxID
                )
            )
        }
    }
}
