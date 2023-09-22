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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fishbowl.adapters.QuestionListAdapter
import com.example.fishbowl.adapters.SwipeToDeleteCallback
import com.example.fishbowl.databinding.FragmentQuestionListBinding
import com.example.fishbowl.modals.Question
import com.example.fishbowl.viewmodal.GameViewModel
import com.google.android.material.snackbar.Snackbar

class QuestionListFragment : Fragment() {

    private var _binding: FragmentQuestionListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels();

    private val questions = arrayListOf<Question>()
    private val questionListAdapter = QuestionListAdapter(questions)

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

        // Swipe feature to delete.
        val swipeHandler = object: SwipeToDeleteCallback(context) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition;

                // Create option to undo the deletion of question.
                val snackbar = Snackbar.make(binding.root, "Vraag verwijderd!", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo verwijderen", UndoDeleteQuestionListener(questions[position]))
                snackbar.show()

                viewModel.deleteQuestion(questions[position])

                questionListAdapter.notifyDataSetChanged()
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.rvQuestions)

        if (questionsType != null) {
            observeQuestions(questionsType)
        }
    }

    // When user undoes the deletion of a question.
    inner class UndoDeleteQuestionListener(question: Question) : View.OnClickListener {
        private var question: Question? = null
        init {
            this.question = question
        }

        override fun onClick(p0: View?) {
            viewModel.insertQuestion(this.question!!)
            Snackbar.make(binding.root, "Ongedaan gemaakt van verwijderen!", Snackbar.LENGTH_SHORT).show();
            questionListAdapter.notifyDataSetChanged()
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