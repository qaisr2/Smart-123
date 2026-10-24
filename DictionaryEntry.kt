package com.example.model

/**
 * Detailed dictionary entry with Urdu script, Roman Urdu, simple definitions, and synonyms.
 */
data class DictionaryEntry(
    val word: String,
    val urduMeaning: String,
    val romanUrdu: String,
    val partOfSpeech: String = "noun",
    val simpleEnglish: String,
    val simpleUrdu: String,
    val synonyms: List<String> = emptyList(),
    val exampleSentence: String = "",
    val contextualSenses: List<ContextSense> = emptyList()
)

data class ContextSense(
    val triggerKeywords: List<String>,
    val contextName: String,
    val urduMeaning: String,
    val romanUrdu: String,
    val explanation: String
)
