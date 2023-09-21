package com.example.fishbowl.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fishbowl.R
import com.example.fishbowl.databinding.FragmentAddPlayersBinding
import com.example.fishbowl.databinding.FragmentChooseBinding
import com.example.fishbowl.modals.Player
import com.example.fishbowl.viewmodal.GameViewModel

class AddPlayersFragment : Fragment() {

    private var _binding: FragmentAddPlayersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        binding.toggleButton.setOnCheckedChangeListener{_, isChecked ->

            if (isChecked) {
                binding.toggleButton.setBackgroundResource(R.color.for_woman);
            } else {
                binding.toggleButton.setBackgroundResource(R.color.for_man)
            }
        }

        binding.btnAdd.setOnClickListener{
            addNewPlayer()
        }

        observeGame()
    }

    private fun observeGame() {
        viewModel.success.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        })
    }

    private fun addNewPlayer() {
        if (binding.editText.text.isNullOrEmpty()) {
            Toast.makeText(context, "Vul een naam in a sukkel!", Toast.LENGTH_SHORT).show()
            return;
        }

        viewModel.insertPlayer(Player(binding.editText.text.toString(),
            binding.toggleButton.isChecked))
    }
}