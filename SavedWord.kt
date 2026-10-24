package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_words")
data class SavedWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String,
    val urduMeaning: String,
    val romanUrdu: String,
    val partOfSpeech: String = "",
    val simpleEnglish: String = "",
    val simpleUrdu: String = "",
    val synonyms: String = "", // Comma-separated
    val contextSentence: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val masteryLevel: Int = 0, // 0 = Learning, 1 = Reviewing, 2 = Mastered
    val notes: String = ""
)

@Entity(tableName = "search_history")
data class SearchHistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val query: String,
    val urduPreview: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
