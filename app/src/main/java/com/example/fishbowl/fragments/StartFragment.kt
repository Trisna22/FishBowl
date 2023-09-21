package com.example.fishbowl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fishbowl.R
import com.example.fishbowl.adapters.PlayerAdapter
import com.example.fishbowl.databinding.FragmentStartBinding
import com.example.fishbowl.modals.Player
import com.example.fishbowl.viewmodal.GameViewModel
import com.google.android.material.snackbar.Snackbar

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels();

    private val players = arrayListOf<Player>();
    private val playerAdapter = PlayerAdapter(players, ::deletePlayer);

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPlayers.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        binding.rvPlayers.adapter = playerAdapter;
        binding.rvPlayers.addItemDecoration(DividerItemDecoration(binding.rvPlayers.context, RecyclerView.VERTICAL))

        binding.fabAdd.setOnClickListener{
            findNavController().navigate(R.id.action_startFragment_to_addPlayersFragment)
        }

        binding.btnStart.setOnClickListener {

            if (players.size == 0) {
                Toast.makeText(
                    context,
                    "Je kan niet zonder spelers spelen, dombo!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else if (players.size == 1) {
                Toast.makeText(
                    context,
                    "Je kan niet alleen spelen, heb je geen vrienden ofzo?",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            findNavController().navigate(R.id.action_startFragment_to_chooseFragment);
        }

        binding.ivInfo.setOnClickListener{
            findNavController().navigate(R.id.action_startFragment_to_creditsFragment)
        }

        observerAddPlayers()

        binding.ivSettings.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
        }
    }

    private fun deletePlayer(player: Player) {
        viewModel.deletePlayer(player)
    }

    private fun observerAddPlayers() {
        viewModel.players.observe(viewLifecycleOwner, Observer { newPlayers ->
            players.clear()
            players.addAll(newPlayers)
            playerAdapter.notifyDataSetChanged()
        })
    }

}