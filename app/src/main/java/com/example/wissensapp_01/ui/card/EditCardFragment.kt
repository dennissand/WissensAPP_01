package com.example.wissensapp_01.ui.card

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.databinding.FragmentCardEditBinding

class EditCardFragment : Fragment() {

    private var _binding: FragmentCardEditBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var cardId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: MainViewModel by activityViewModels()

        _binding = FragmentCardEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.let { cardId = it.getString("cardId").toString() }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var boxID = ""
        val editcard = viewModel.getCardById(cardId)
        if (editcard != null){
            binding.eTAEdit.setText(editcard.a)
            binding.eTBEdit.setText(editcard.b)
            val boxes = viewModel.boxes
            val boxnames = boxes.value?.map { it.boxName }?.toTypedArray()

            if (boxnames != null) {
                val spinnerAdapter =
                    ArrayAdapter(requireContext(), R.layout.simple_spinner_item, boxnames)
                binding.dropDownCardEdit.adapter = spinnerAdapter
                binding.dropDownCardEdit.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        // Log.e("---", "$position $id")
                        boxID = boxes.value!![position].boxId
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
                val selectedBox = boxes.value!!.find { it.boxId == boxID }
                binding.dropDownCardEdit.setSelection(boxes.value!!.indexOf(selectedBox))
            }
        }


    }
}
