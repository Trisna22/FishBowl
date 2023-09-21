package com.example.fishbowl.modals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(

    @ColumnInfo(name = "questionText")
    var questionText: String,

    @ColumnInfo(name = "questionType")
    var questionType: Int,

    @ColumnInfo(name = "requireOtherPlayer")
    var requireOtherPlayer: Boolean,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)

/**
 * Question type table
 * 0 EVERYONE
 * 1 PERSONAL
 * 2 PERSONAL WITH OTHER PLAYER
 * 3 VOTES
 * 4 QUIZ
 */