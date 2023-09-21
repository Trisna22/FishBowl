package com.example.fishbowl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.fishbowl.R
import com.example.fishbowl.databinding.FragmentSettingsBinding
import com.example.fishbowl.databinding.FragmentStartBinding

const val ARG_QUESTION_TYPE = "QUESTION_TYPE"
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        binding.cvEverybody.setOnClickListener {
            val bundle = bundleOf(Pair(ARG_QUESTION_TYPE, 0))
            findNavController().navigate(R.id.action_settingsFragment_to_questionListFragment, bundle)
        }

        binding.cvPersonal.setOnClickListener {
            val bundle = bundleOf(Pair(ARG_QUESTION_TYPE, 1))
            findNavController().navigate(R.id.action_settingsFragment_to_questionListFragment, bundle)
        }

        binding.cvPersonal2.setOnClickListener {
            val bundle = bundleOf(Pair(ARG_QUESTION_TYPE, 2))
            findNavController().navigate(R.id.action_settingsFragment_to_questionListFragment, bundle)
        }

        binding.cvVotes.setOnClickListener {
            val bundle = bundleOf(Pair(ARG_QUESTION_TYPE, 3))
            findNavController().navigate(R.id.action_settingsFragment_to_questionListFragment, bundle)
        }

        binding.cvQuiz.setOnClickListener {
            val bundle = bundleOf(Pair(ARG_QUESTION_TYPE, 4))
            findNavController().navigate(R.id.action_settingsFragment_to_questionListFragment, bundle)
        }
    }
}