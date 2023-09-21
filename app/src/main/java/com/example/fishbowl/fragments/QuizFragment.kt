package com.example.fishbowl.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishbowl.R
import com.example.fishbowl.databinding.FragmentQuizBinding
import com.example.fishbowl.databinding.FragmentVotesBinding
import kotlin.random.Random

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var revealAnswer = false;
    private var question: String = "VRAAG"
    private var answer: String = "ANTWOORD"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun quizButtonClick() {
        if (revealAnswer) {
            binding.tvQuestion.text = getString(R.string.de_vraag, question)
            binding.btnQuiz.text = getString(R.string.laat_antwoord_zien)
            binding.btnQuiz.setBackgroundColor(Color.BLUE)

        } else {
            binding.tvQuestion.text = getString(R.string.het_antwoord_is, answer)
            binding.btnQuiz.text = getString(R.string.laat_vraag_zien)
            binding.btnQuiz.setBackgroundColor(Color.RED)
        }
        revealAnswer = !revealAnswer
    }

    private fun initViews() {

        // Randomly select image.
        when (Random.nextInt(13)) {
            0 -> binding.ivFish.setImageResource(R.drawable.shark)
            1 -> binding.ivFish.setImageResource(R.drawable.gold_fish)
            2 -> binding.ivFish.setImageResource(R.drawable.king)
            3 -> binding.ivFish.setImageResource(R.drawable.small_fish1)
            4 -> binding.ivFish.setImageResource(R.drawable.small_fish2)
            5 -> binding.ivFish.setImageResource(R.drawable.small_fish3)
            6 -> binding.ivFish.setImageResource(R.drawable.small_fish4)
            7 -> binding.ivFish.setImageResource(R.drawable.dory)
            8 -> binding.ivFish.setImageResource(R.drawable.blue_fish)
            9 -> binding.ivFish.setImageResource(R.drawable.group_fish2)
            10 -> binding.ivFish.setImageResource(R.drawable.nemo)
            11 -> binding.ivFish.setImageResource(R.drawable.yellow_fish)
            12 -> binding.ivFish.setImageResource(R.drawable.fish)
            else -> binding.ivFish.setImageResource(R.drawable.fishbowl)
        }

        val wholeQuestion = arguments?.getString(ARG_QUESTION)
        val otherPlayer = arguments?.getString(ARG_OTHER_PLAYER)
        val player = arguments?.getString(ARG_PLAYER)

        if (wholeQuestion != null) {
            question = wholeQuestion.substring(0, wholeQuestion.indexOf("#"))
            answer = wholeQuestion.substring(wholeQuestion.indexOf("#") +1)
        }

        binding.tvPlayers.text = getString(R.string.vs, player, otherPlayer)
        binding.tvQuestion.text = getString(R.string.de_vraag, question)
        binding.btnQuiz.setBackgroundColor(Color.BLUE)

        binding.btnNext.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnQuiz.setOnClickListener {
            quizButtonClick()
        }
    }
}