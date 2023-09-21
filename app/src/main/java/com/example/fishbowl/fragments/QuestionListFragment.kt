package com.example.fishbowl.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fishbowl.adapters.QuestionListAdapter
import com.example.fishbowl.databinding.FragmentQuestionListBinding
import com.example.fishbowl.databinding.FragmentSettingsBinding
import com.example.fishbowl.modals.Question
import com.example.fishbowl.viewmodal.GameViewModel

class QuestionListFragment : Fragment() {

    private var _binding: FragmentQuestionListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels();

    private val questions = arrayListOf<Question>()
    private val questionListAdapter = QuestionListAdapter(questions, ::onDeleteQuestion)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun onDeleteQuestion(question: Question) {

        Log.d("TEST", "Deleting question...")
    }

    private fun initViews() {

        val questionsType = arguments?.getInt(ARG_QUESTION_TYPE)
        binding.tvQuestionType.text = when (questionsType) {
            0 -> "Vragen voor de hele groep"
            1 -> "Persoonlijke vragen"
            2 -> "Vragen voor 2 personen"
            3 -> "Stem vragen"
            4 -> "Quiz vragen"
            else -> {
                "Onbekende type vragen..."
            }
        }

        binding.rvQuestions.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionListAdapter
        binding.rvQuestions.addItemDecoration(DividerItemDecoration(binding.rvQuestions.context, RecyclerView.VERTICAL))

        if (questionsType != null) {
            observeQuestions(questionsType)
        }
    }

    private fun observeQuestions(questionType: Int) {
        when (questionType) {
            0 -> viewModel.questionsForEveryone.observe(viewLifecycleOwner, Observer { allQuestions ->
                questions.clear()
                questions.addAll(allQuestions)
                questionListAdapter.notifyDataSetChanged()
            })
            1 -> viewModel.questionsForPersonal.observe(viewLifecycleOwner, Observer { allQuestions ->
                questions.clear()
                questions.addAll(allQuestions)
                questionListAdapter.notifyDataSetChanged()
            })
            2 -> viewModel.questionsForPersonal2.observe(viewLifecycleOwner, Observer { allQuestions ->
                questions.clear()
                questions.addAll(allQuestions)
                questionListAdapter.notifyDataSetChanged()
            })
            3 -> viewModel.questionsForVotes.observe(viewLifecycleOwner, Observer { allQuestions ->
                questions.clear()
                questions.addAll(allQuestions)
                questionListAdapter.notifyDataSetChanged()
            })
            4 -> viewModel.questionsForQuiz.observe(viewLifecycleOwner, Observer { allQuestions ->
                questions.clear()
                questions.addAll(allQuestions)
                questionListAdapter.notifyDataSetChanged()
            })
            else -> Toast.makeText(
                context,
                "Onbekende questionType!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}