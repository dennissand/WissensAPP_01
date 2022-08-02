package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wissensapp_01.FirestoreStatus
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.adapter.BoxAdapter
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.databinding.FragmentBoxHomeBinding

class BoxFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentBoxHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoxHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reBoxView = binding.rwBoxHome
        val adapter = BoxAdapter(emptyList(), deleteBox, navtoDetail)
        reBoxView.adapter = adapter
        viewModel.boxes.observe(
            viewLifecycleOwner
        ) {
            adapter.submitBoxList(it)
        }

        binding.btnBoxAdd.setOnClickListener {
            findNavController().navigate(BoxFragmentDirections.actionBoxFragmentToAddBoxFragment())
        }

        viewModel.boxloading.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                FirestoreStatus.LOADING -> binding.progressBarBox.visibility = View.VISIBLE
                FirestoreStatus.ERROR -> {
                    binding.progressBarBox.visibility = View.GONE
                }
                else -> {
                    binding.progressBarBox.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val deleteBox = { box: Box ->
        viewModel.deleteBox(box, requireContext())
    }

    private val navtoDetail = { id: String ->
        findNavController().navigate(
            BoxFragmentDirections.actionBoxFragmentToDetailBoxFragment(id)
        )
    }
}
