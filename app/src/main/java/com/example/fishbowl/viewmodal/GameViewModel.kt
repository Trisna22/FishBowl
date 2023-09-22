package com.example.fishbowl.viewmodal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fishbowl.modals.Player
import com.example.fishbowl.modals.Question
import com.example.fishbowl.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application): AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.IO)

    val players = gameRepository.getAllPlayers()
    val questionsPersonal = gameRepository.getAllQuestionsPersonal()
    val questionsEveryone = gameRepository.getAllQuestionsEveryone()

    val questionsForEveryone = gameRepository.getAllEveryoneQuestions()
    val questionsForQuiz = gameRepository.getAllQuestionsQuiz()
    val questionsForPersonal = gameRepository.getAllQuestionsForPersonal()
    val questionsForPersonal2 = gameRepository.getAllQuestionsForPersonal2()
    val questionsForVotes = gameRepository.getAllQuestionsForVotes()

    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun insertPlayer(player: Player) {
        mainScope.launch {
            gameRepository.insertPlayer(player)
        }
        success.value = true
    }

    fun insertQuestion(question: Question) {
        mainScope.launch {
            gameRepository.insertQuestion(question)
        }
    }

    fun deletePlayer(player: Player) {
        mainScope.launch {
            gameRepository.deletePlayer(player)
        }
    }

    fun deleteQuestion(question: Question) {
        mainScope.launch {
            gameRepository.deleteQuestion(question)
        }
    }
}