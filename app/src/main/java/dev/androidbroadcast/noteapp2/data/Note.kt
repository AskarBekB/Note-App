package dev.androidbroadcast.noteapp2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: Long, // храним дату как Long
    val text: String
)