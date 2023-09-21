package com.example.fishbowl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishbowl.R
import com.example.fishbowl.databinding.QuestionItemBinding
import com.example.fishbowl.modals.Question


class QuestionListAdapter(private val questions: List<Question>, val deleteQuestion: (question: Question) -> Unit) : RecyclerView.Adapter<QuestionListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = QuestionItemBinding.bind(itemView)

        fun databind(question: Question) {

            binding.tvQuestion.text = question.questionText;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionListAdapter.ViewHolder, position: Int) {
        return holder.databind(questions[position]);
    }

    override fun getItemCount(): Int {
        return questions.size;
    }
}