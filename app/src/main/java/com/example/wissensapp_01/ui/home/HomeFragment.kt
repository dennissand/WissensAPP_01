package com.example.wissensapp_01.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase Auth wird abgerufen //
        firebaseAuth = FirebaseAuth.getInstance()

        // LogOut Funktion wenn man den Log verlassen möchte //
        binding.logOut.setOnClickListener {
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            val builder = AlertDialog.Builder(context)
            builder.setTitle("App verlassen ?")
                .setMessage("Bist Du sicher das Du die App verlassen willst ?")
                .setPositiveButton(
                    "Verlassen !",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        firebaseAuth.signOut()
                        startActivity(intent)
                    }
                )
                .setNegativeButton(
                    "Bleiben !",
                    DialogInterface.OnClickListener { dialogInterface, i -> }
                )
                .create()
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
