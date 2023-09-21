package com.example.fishbowl.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fishbowl.modals.Player
import com.example.fishbowl.modals.Question

@Dao
interface GameDao {

    @Query("SELECT * FROM questions WHERE questionType = 1 OR questionType = 2 OR questionType = 4")
    fun getAllQuestionsPersonal(): LiveData<List<Question>>

    @Query("SELECT * FROM questions WHERE questionType = 0 OR questionType = 3")
    fun getAllQuestionsEveryone(): LiveData<List<Question>>

    @Query("SELECT * FROM questions WHERE questionType = 0")
    fun getAllQuestionsForEveryone(): LiveData<List<Question>>

    @Query("SELECT * FROM questions WHERE questionType = 1")
    fun getAllQuestionsForPersonal(): LiveData<List<Question>>

    @Query("SELECT * FROM questions WHERE questionType = 2")
    fun getAllQuestionsForPersonal2() : LiveData<List<Question>>

    @Query("SELECT * FROM questions WHERE questionType = 3")
    fun getAllQuestionsForVotes(): LiveData<List<Question>>

    @Query("SELECT * FROM questions WHERE questionType = 4")
    fun getAllQuestionsQuiz(): LiveData<List<Question>>

    @Query("DELETE FROM questions")
    fun deleteAllQuestions()

    @Insert
    suspend fun insertQuestion(question: Question)

    @Query("SELECT * FROM players")
    fun getAllPlayers(): LiveData<List<Player>>

    @Insert
    suspend fun insertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)
}
