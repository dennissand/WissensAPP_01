package com.example.wissensapp_01.ui.learn

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.R
import com.example.wissensapp_01.adapter.LearnDetailAdapter
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.databinding.FragmentLearnDetailBinding

class LearnDetailFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentLearnDetailBinding? = null
    private val binding get() = _binding!!
    private var showlearncard: Boolean = false

    lateinit var animFront: AnimatorSet
    lateinit var animBack: AnimatorSet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { showlearncard = it.getBoolean("showlearncard") }

        _binding = FragmentLearnDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scale: Float = requireContext().resources.displayMetrics.density
        animFront = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.front_animator
        ) as AnimatorSet
        animBack =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.back_animator) as AnimatorSet

        val cardList = viewModel.cards.value?.filter { it.cardLearned == showlearncard }
        val reDetailView = binding.rwCardLearnDetail
        val adapter =
            cardList?.let { LearnDetailAdapter(it, animFront, animBack, scale, cardtoggeld) }
        reDetailView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val cardtoggeld = { card: Card, cardLearned: Boolean ->
        viewModel.cardtoggeld(card, cardLearned)


    }
}
