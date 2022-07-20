package com.example.wissensapp_01.ui.learn

import LearnCardAdapter
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.wissensapp_01.MainViewModel
import com.example.wissensapp_01.R
import com.example.wissensapp_01.data.model.Card
import com.example.wissensapp_01.databinding.FragmentLearnCardBinding

class LearnCardFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentLearnCardBinding? = null
    private val binding get() = _binding!!
    private var boxID: String = ""

    lateinit var animFront: AnimatorSet
    lateinit var animBack: AnimatorSet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { boxID = it.getString("boxID").toString() }

        _binding = FragmentLearnCardBinding.inflate(inflater, container, false)
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

        while (viewModel.cards.value?.isEmpty() == true) {
        }

        viewModel.getLearnCards(boxID)
        val reLearnView = binding.rwCardLearn
        val adapter = LearnCardAdapter(emptyList(), animFront, animBack, scale, cardtoggeld)
        reLearnView.adapter = adapter
        try {
            viewModel.learncards.observe(
                viewLifecycleOwner,
                Observer {
                    if (it != null) {
                        adapter.submitLearnCardList(it)
                    }
                }
            )
        } catch (e: Exception) {
            Log.e("LearnCardFragment", e.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val cardtoggeld = { card: Card, cardLearned: Boolean ->
        viewModel.cardtoggeld(card, cardLearned)
    }
}
