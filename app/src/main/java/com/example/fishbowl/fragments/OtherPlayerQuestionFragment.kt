package com.example.fishbowl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishbowl.R
import com.example.fishbowl.databinding.FragmentChooseBinding
import com.example.fishbowl.databinding.FragmentOtherPlayerQuestionBinding
import kotlin.random.Random

class OtherPlayerQuestionFragment : Fragment() {
    private var _binding: FragmentOtherPlayerQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtherPlayerQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
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

        val question = arguments?.getString(ARG_QUESTION)
        val otherPlayer = arguments?.getString(ARG_OTHER_PLAYER)
        val player = arguments?.getString(ARG_PLAYER)

        binding.tvPlayer.text = "$player & $otherPlayer"

        if (question != null) {
            binding.tvQuestion.text = question
                .replace("{PLAYER}", player!!)
                .replace("{OTHER_PLAYER}",
                otherPlayer!!
            )
        }

        binding.btnNext.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}