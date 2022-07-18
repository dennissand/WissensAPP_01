package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.adapter.BoxCardAdapter
import com.example.wissensapp_01.databinding.FragmentBoxDetailBinding

class DetailBoxFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
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
        val adapter = BoxCardAdapter(emptyList())
        reDetailView.adapter = adapter
        viewModel.boxcards.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitBoxCardList(it)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
