package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.databinding.FragmentBoxEditBinding

class EditBoxFragment : Fragment() {

    private var _binding: FragmentBoxEditBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var boxId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoxEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.let { boxId = it.getString("boxId").toString() }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editbox = viewModel.getBoxByID(boxId)

        binding.btnSaveBoxEdit.setOnClickListener {
            if (editbox != null) {
                editbox.boxName = binding.eTBoxNameEdit.text.toString()
                editbox.boxContent = binding.eTBoxContentEdit.text.toString()
                viewModel.updateBox(editbox)
                binding.eTBoxNameEdit.setText("")
                binding.eTBoxContentEdit.setText("")
                Toast.makeText(context, "Box geändert und gespeichert !", Toast.LENGTH_LONG).show()
            }
        }
        if (editbox != null) {
            binding.eTBoxNameEdit.setText(editbox.boxName)
            binding.eTBoxContentEdit.setText(editbox.boxContent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
