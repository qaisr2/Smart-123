package com.example.data.dictionary

import com.example.model.DictionaryEntry
import java.util.Locale

object OfflineDictionary {

    private val baseDictionary = DictionaryData.entries

    /**
     * Look up word with morphological normalization and context disambiguation.
     */
    fun lookup(rawWord: String, surroundingSentence: String = ""): DictionaryEntry {
        val clean = rawWord.trim().lowercase(Locale.ENGLISH).replace(Regex("[^a-z]"), "")
        if (clean.isBlank()) {
            return synthesizeMeaning(rawWord, surroundingSentence)
        }

        // 1. Direct match
        val directMatch = baseDictionary[clean]
        if (directMatch != null) {
            return disambiguateWithContext(directMatch, surroundingSentence)
        }

        // 2. Stemming candidates
        val candidates = generateStemCandidates(clean)
        for (cand in candidates) {
            val match = baseDictionary[cand]
            if (match != null) {
                return disambiguateWithContext(match, surroundingSentence).copy(word = clean)
            }
        }

        // 3. Smart Heuristic synthesis
        return synthesizeMeaning(clean, surroundingSentence)
    }

    /**
     * Disambiguate words that have multiple senses using context keywords.
     */
    private fun disambiguateWithContext(entry: DictionaryEntry, sentence: String): DictionaryEntry {
        if (entry.contextualSenses.isEmpty() || sentence.isBlank()) {
            return entry
        }

        val lowerSentence = sentence.lowercase(Locale.ENGLISH)
        val sentenceTokens = lowerSentence.split(Regex("[^a-z0-9]+")).filter { it.isNotBlank() }.toSet()

        // Score each sense by number of keyword matches
        var bestSense: com.example.model.ContextSense? = null
        var maxMatches = 0

        for (sense in entry.contextualSenses) {
            val count = sense.triggerKeywords.count { kw ->
                sentenceTokens.contains(kw) || sentenceTokens.any { token ->
                    (token.startsWith(kw) || kw.startsWith(token)) && kw.length >= 4
                }
            }
            if (count > maxMatches) {
                maxMatches = count
                bestSense = sense
            }
        }

        if (bestSense != null && maxMatches > 0) {
            return entry.copy(
                urduMeaning = "${bestSense.urduMeaning} (${bestSense.contextName})",
                romanUrdu = "${bestSense.romanUrdu} [${bestSense.contextName}]",
                simpleUrdu = bestSense.explanation,
                simpleEnglish = "${entry.simpleEnglish} (Sense: ${bestSense.contextName})"
            )
        }

        return entry
    }

    private fun generateStemCandidates(word: String): List<String> {
        val list = mutableListOf<String>()
        // Plurals
        if (word.endsWith("ies") && word.length > 4) {
            list.add(word.dropLast(3) + "y")
        }
        if (word.endsWith("es") && word.length > 3) {
            list.add(word.dropLast(2))
            list.add(word.dropLast(1))
        }
        if (word.endsWith("s") && word.length > 2) {
            list.add(word.dropLast(1))
        }
        // Past / Participle
        if (word.endsWith("ing") && word.length > 4) {
            list.add(word.dropLast(3))
            list.add(word.dropLast(3) + "e")
        }
        if (word.endsWith("ed") && word.length > 3) {
            list.add(word.dropLast(2))
            list.add(word.dropLast(1))
        }
        // Adverbs & Adjectives
        if (word.endsWith("ly") && word.length > 3) {
            list.add(word.dropLast(2))
            if (word.endsWith("ily")) list.add(word.dropLast(3) + "y")
        }
        if (word.endsWith("er") && word.length > 3) {
            list.add(word.dropLast(2))
            list.add(word.dropLast(1))
        }
        if (word.endsWith("est") && word.length > 4) {
            list.add(word.dropLast(3))
            list.add(word.dropLast(2))
        }
        return list
    }

    private fun synthesizeMeaning(word: String, sentence: String): DictionaryEntry {
        // Common suffix/prefix heuristics
        val (pos, urduPos) = when {
            word.endsWith("tion") || word.endsWith("sion") || word.endsWith("ment") || word.endsWith("ness") ->
                Pair("noun", "اسم / کیفیت")
            word.endsWith("ly") ->
                Pair("adverb", "متعلق فعل (طور پر)")
            word.endsWith("able") || word.endsWith("ible") || word.endsWith("ous") || word.endsWith("ful") || word.endsWith("ive") ->
                Pair("adjective", "صفت / خوبی")
            word.endsWith("ize") || word.endsWith("ise") || word.endsWith("ify") ->
                Pair("verb", "فعل / کام")
            else -> Pair("word", "لفظ")
        }

        return DictionaryEntry(
            word = word,
            urduMeaning = "انگریزی لفظ: $word ($urduPos)",
            romanUrdu = "$word ($pos)",
            partOfSpeech = pos,
            simpleEnglish = "An English $pos found in your reading text. You can save it or use Contextual AI for detailed etymology.",
            simpleUrdu = "یہ لفظ آپ کے منتخب کردہ پیراگراف میں استعمال ہوا ہے۔ آپ اسے اپنی ذخیرہ الفاظ (Vocabulary) میں محفوظ کر سکتے ہیں۔",
            synonyms = listOf("term", "vocabulary", "expression"),
            exampleSentence = if (sentence.isNotBlank()) sentence else "The word '$word' is used in this reading context."
        )
    }

    /**
     * Local sentence / paragraph translation into Urdu.
     * Uses structured clause breakdown and bilingual word-mapping.
     */
    fun translateSentenceLocally(sentence: String): String {
        if (sentence.isBlank()) return ""
        val trimmed = sentence.trim()

        // Quick common phrase pattern translations
        val commonPatterns = listOf(
            Regex("(?i)^learning english is (.*)") to "انگریزی سیکھنا $1 ہے",
            Regex("(?i)^it is important to (.*)") to "$1 بے حد ضروری ہے",
            Regex("(?i)^there is (.*)") to "وہاں $1 موجود ہے",
            Regex("(?i)^there are (.*)") to "وہاں $1 موجود ہیں",
            Regex("(?i)^in order to (.*)") to "$1 کے مقصد سے",
            Regex("(?i)^according to (.*)") to "$1 کے مطابق",
            Regex("(?i)^daily practice helps (.*)") to "روزانہ کی مشق $1 میں مدد دیتی ہے"
        )

        for ((regex, replacement) in commonPatterns) {
            if (regex.containsMatchIn(trimmed)) {
                return regex.replace(trimmed, replacement)
            }
        }

        // Word-by-word contextual translation generator
        val tokens = trimmed.split(Regex("\\s+"))
        val translatedTokens = tokens.map { rawToken ->
            val clean = rawToken.lowercase(Locale.ENGLISH).replace(Regex("[^a-z]"), "")
            val entry = baseDictionary[clean]
            if (entry != null) {
                // Take first meaning part before slash
                entry.urduMeaning.split("/").first().trim()
            } else {
                when (clean) {
                    "the", "a", "an" -> ""
                    "is" -> "ہے"
                    "are" -> "ہیں"
                    "was" -> "تھا"
                    "were" -> "تھے"
                    "and" -> "اور"
                    "or" -> "یا"
                    "but" -> "لیکن"
                    "in" -> "میں"
                    "on" -> "پر"
                    "at" -> "پر"
                    "to" -> "کی طرف"
                    "for" -> "کے لیے"
                    "with" -> "کے ساتھ"
                    "by" -> "کے ذریعے"
                    "from" -> "سے"
                    "of" -> "کا"
                    "this" -> "یہ"
                    "that" -> "وہ"
                    "these" -> "یہ سب"
                    "those" -> "وہ سب"
                    "he", "she", "it" -> "وہ"
                    "they" -> "وہ"
                    "we" -> "ہم"
                    "you" -> "آپ"
                    "i" -> "میں"
                    "my" -> "میرا"
                    "your" -> "آپ کا"
                    "our" -> "ہمارا"
                    "their" -> "ان کا"
                    "his", "her" -> "اس کا"
                    "not" -> "نہیں"
                    "can" -> "سکتا ہے"
                    "will" -> "گا / گی"
                    "must" -> "ضرور"
                    "should" -> "چاہیے"
                    "have", "has" -> "پاس ہے"
                    "had" -> "پاس تھا"
                    "do", "does" -> "کرتا ہے"
                    "did" -> "کیا"
                    "read", "reading" -> "پڑھنا"
                    "write", "writing" -> "لکھنا"
                    "speak", "speaking" -> "بولنا"
                    "listen", "listening" -> "سننا"
                    "learn", "learning" -> "سیکھنا"
                    "help", "helps" -> "مدد"
                    "good" -> "اچھا"
                    "great" -> "عظیم"
                    "new" -> "نیا"
                    "day", "days" -> "دن"
                    "time" -> "وقت"
                    "book", "books" -> "کتاب"
                    "page", "pages" -> "صفحہ"
                    "word", "words" -> "الفاظ"
                    "sentence" -> "جملہ"
                    else -> rawToken
                }
            }
        }.filter { it.isNotBlank() }

        return if (translatedTokens.isNotEmpty()) {
            translatedTokens.joinToString(" ") + "۔"
        } else {
            "ترجمہ دستیاب نہیں ہے۔"
        }
    }
}
