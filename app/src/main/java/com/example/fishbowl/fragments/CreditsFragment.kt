package com.example.fishbowl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fishbowl.R
import com.example.fishbowl.databinding.FragmentCreditsBinding
import com.example.fishbowl.databinding.FragmentEveryoneQuestionBinding
import kotlin.random.Random

class CreditsFragment : Fragment() {

    private var _binding: FragmentCreditsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
// Inflate the layout for this fragment
        _binding = FragmentCreditsBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (Random.nextInt(3)) {
            0 -> binding.ivMe.setImageResource(R.drawable.me_bench)
            1 -> binding.ivMe.setImageResource(R.drawable.me_walking)
            2 -> binding.ivMe.setImageResource(R.drawable.me_fishbowl)
        }
    }
}