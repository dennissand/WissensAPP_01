package com.example.wissensapp_01.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wissensapp_01.SignUpActivity
import com.example.wissensapp_01.databinding.FragmentHomeHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeHomeBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase wird abgerufen //
        firebaseAuth = FirebaseAuth.getInstance()

        // LogOut Funktion wenn man den Log verlassen möchte //
        binding.logOut.setOnClickListener {
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            firebaseAuth.signOut()
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
