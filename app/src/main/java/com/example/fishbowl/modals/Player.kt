package com.example.fishbowl.modals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "players")
data class Player(
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "isWoman")
    var isWoman: Boolean,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)
