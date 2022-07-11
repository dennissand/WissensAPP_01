package com.example.wissensapp_01.ui.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.databinding.FragmentBoxAddBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class AddBoxFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val boxCollectionRef = Firebase.firestore.collection("boxes")
    private var _binding: FragmentBoxAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoxAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveBox.setOnClickListener {
            val box = createBox()
            saveBox(box)
        }
    }

    private fun createBox(): Box {
        val boxId = UUID.randomUUID().toString()
        val boxName = binding.eTBoxName.text.toString()
        val boxContent = binding.eTBoxContent.text.toString()
        return Box(
            boxId = boxId,
            boxName = boxName,
            boxContent = boxContent
        )
    }

    private fun saveBox(box: Box) = CoroutineScope(Dispatchers.IO).launch {
        try {
            boxCollectionRef.add(box).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    "Successfully saved data.",
                    Toast.LENGTH_LONG
                )
                    .show()
                binding.eTBoxName.setText("")
                binding.eTBoxContent.setText("")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
