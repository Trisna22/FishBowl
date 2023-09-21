package com.example.fishbowl.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.fishbowl.dao.GameDao
import com.example.fishbowl.db.GameRoomDatabase
import com.example.fishbowl.modals.Player
import com.example.fishbowl.modals.Question

class GameRepository(context: Context) {

    private var gameDao: GameDao

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    fun getAllPlayers() = gameDao.getAllPlayers()
    fun getAllQuestionsEveryone() = gameDao.getAllQuestionsEveryone()
    fun getAllQuestionsPersonal() = gameDao.getAllQuestionsPersonal()
    fun getAllEveryoneQuestions() = gameDao.getAllQuestionsForEveryone()
    fun getAllQuestionsForPersonal() = gameDao.getAllQuestionsForPersonal()
    fun getAllQuestionsForPersonal2() = gameDao.getAllQuestionsForPersonal2()
    fun getAllQuestionsForVotes() = gameDao.getAllQuestionsForVotes()
    fun getAllQuestionsQuiz() = gameDao.getAllQuestionsQuiz()

    suspend fun insertPlayer(player: Player) = gameDao.insertPlayer(player)

    suspend fun deletePlayer(player: Player) = gameDao.deletePlayer(player)

}