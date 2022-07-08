package com.example.wissensapp_01.ui.learn

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wissensapp_01.R
import com.example.wissensapp_01.databinding.FragmentLearnCardBinding

class LearnCardFragment : Fragment() {

    private var _binding: FragmentLearnCardBinding? = null

    // Flipcard Animation
    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    private var isFront = true

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val learnViewModel =
            ViewModelProvider(this).get(LearnViewModel::class.java)

        _binding = FragmentLearnCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scale: Float = requireContext().resources.displayMetrics.density
        binding.mcvCardFront.cameraDistance = 7000 * scale
        binding.mcvCardBack.cameraDistance = 7000 * scale

        front_anim = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.front_animator
        ) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.back_animator
        ) as AnimatorSet

        binding.mcvCardFront.setOnClickListener {
            if (isFront) {
                front_anim.setTarget(binding.mcvCardFront)
                back_anim.setTarget(binding.mcvCardBack)
                front_anim.start()
                back_anim.start()
                isFront = false
            } else {
                front_anim.setTarget(binding.mcvCardBack)
                back_anim.setTarget(binding.mcvCardFront)
                back_anim.start()
                front_anim.start()
                isFront = true
            }
        }
    }
}
