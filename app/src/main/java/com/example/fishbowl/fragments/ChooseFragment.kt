package com.example.fishbowl.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fishbowl.MainActivity
import com.example.fishbowl.R
import com.example.fishbowl.adapters.PlayerAdapter
import com.example.fishbowl.databinding.FragmentChooseBinding
import com.example.fishbowl.modals.Player
import com.example.fishbowl.modals.Question
import com.example.fishbowl.viewmodal.GameViewModel
import kotlin.random.Random

const val ARG_PLAYER = "PLAYER"
const val ARG_OTHER_PLAYER = "OTHER"
const val ARG_QUESTION = "QUESTION"
const val EVERYONE_PLAYER = "IEDEREEN"
class ChooseFragment : Fragment() {

    private var _binding: FragmentChooseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels();
    private val players = arrayListOf<Player>();
    private val questionsPersonal = arrayListOf<Question>()
    private val questionsEveryone = arrayListOf<Question>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAction.setOnClickListener{
            makeChoices()
        }

        observePlayers()
        observeQuestions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun makeChoices() {

        // First choose player.
        val newPlayer = choosePlayer() // Making sure its random.
        binding.tvPlayer.text = newPlayer

        // Now choose a question.
        val question = if (newPlayer.equals(EVERYONE_PLAYER)) {
            chooseQuestion(true)
        } else {
            chooseQuestion(false)
        }

        binding.btnAction.text = getString(R.string.doorgaan)
        binding.btnAction.setBackgroundColor(Color.BLUE);

        binding.btnAction.setOnClickListener {
            when (question.questionType) {
                0 -> {
                    val bundle = bundleOf(Pair(ARG_QUESTION, question.questionText))
                    findNavController().navigate(R.id.action_chooseFragment_to_everyoneQuestionFragment, bundle)
                }
                1 -> {
                    val bundle = bundleOf(Pair(ARG_PLAYER, newPlayer), Pair(ARG_QUESTION, question.questionText))
                    findNavController().navigate(R.id.action_chooseFragment_to_basicQuestionFragment, bundle);
                }
                2 -> {
                    val otherPlayer = choosePlayer(true) // Choose another player
                    val bundle = bundleOf(Pair(ARG_QUESTION, question.questionText), Pair(
                        ARG_OTHER_PLAYER, otherPlayer), Pair(ARG_PLAYER, newPlayer))
                    findNavController().navigate(R.id.action_chooseFragment_to_otherPlayerQuestionFragment, bundle)
                }
                3 -> {
                    val bundle = bundleOf(Pair(ARG_QUESTION, question.questionText))
                    findNavController().navigate(R.id.action_chooseFragment_to_votesFragment, bundle)
                }
                4 -> {
                    val otherPlayer = choosePlayer(true) // Choose another player
                    val bundle = bundleOf(Pair(ARG_QUESTION, question.questionText),
                        Pair(ARG_PLAYER, newPlayer), Pair(ARG_OTHER_PLAYER, otherPlayer))
                    findNavController().navigate(R.id.action_chooseFragment_to_quizFragment, bundle)
                }
                else -> {
                    Toast.makeText(context, "Unknown question type!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun chooseQuestion(forEveryone: Boolean): Question {

        val newQuestion = if (forEveryone) {
            questionsEveryone.asSequence().shuffled().find { true }!!
        } else {
            questionsPersonal.asSequence().shuffled().find { true }!!
        }

        // If the list is empty
        if (MainActivity.lastQuestions.size == 0) {
            MainActivity.lastQuestions.add(newQuestion)
            return newQuestion
        }

        // Check if this question has been chosen last time.
        if (MainActivity.lastQuestions.last() == newQuestion) {
            return chooseQuestion(forEveryone) // Again
        }

        // Check if the question is inside the list.
        if (MainActivity.lastQuestions.indexOf(newQuestion) != -1) {
            return chooseQuestion(forEveryone) // Again
        }

        // For cache cleaning purposes.
        if (MainActivity.lastQuestions.size > 15) {
            MainActivity.lastQuestions.removeFirst()
        }

        return newQuestion
    }

    private fun choosePlayer(exceptEveryone: Boolean = false): String {
        val newPlayer = players.asSequence().shuffled().find { true }!!.username

        // Check if we need to skip ther player "everyone".
        if (exceptEveryone && newPlayer == EVERYONE_PLAYER) {
            return choosePlayer(exceptEveryone)
        }

        if (MainActivity.lastPlayers.size == 0) {
            MainActivity.lastPlayers.add(newPlayer)
            return newPlayer
        }

        // Check if this player has been chosen in the last two times.
        if (MainActivity.lastPlayers.last() == newPlayer) {
            return choosePlayer() // Again.
        }

        // If there are more than 3 players.
        if (MainActivity.lastPlayers.size > 0 && players.size > 3) {
            val oneBeforeLast = MainActivity.lastPlayers[MainActivity.lastPlayers.size -1]
            if (oneBeforeLast == newPlayer) {
                return choosePlayer()
            }
        }

        // For cache cleaning purposes.
        if (MainActivity.lastPlayers.size > players.size) {
            MainActivity.lastPlayers.removeFirst()
        }

        MainActivity.lastPlayers.add(newPlayer)
        return newPlayer
    }


    private fun observePlayers() {
        viewModel.players.observe(viewLifecycleOwner, Observer {
            players.clear();
            players.add(Player(EVERYONE_PLAYER, true))
            players.addAll(it);
        })
    }

    private fun observeQuestions() {
        viewModel.questionsPersonal.observe(viewLifecycleOwner, Observer {
            questionsPersonal.clear()
            questionsPersonal.addAll(it)
        })

        viewModel.questionsEveryone.observe(viewLifecycleOwner, Observer {
            questionsEveryone.clear()
            questionsEveryone.addAll(it)
        })
    }
}