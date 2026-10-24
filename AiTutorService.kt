package com.example.data.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class AiExplanationResult(
    val explanationUrdu: String,
    val explanationEnglish: String,
    val contextNotes: String,
    val suggestedSynonyms: List<String>,
    val sentenceTranslation: String
)

class AiTutorService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    private fun getApiKey(): String {
        return try {
            val field = BuildConfig::class.java.getField("GEMINI_API_KEY")
            field.get(null) as? String ?: ""
        } catch (_: Exception) {
            ""
        }
    }

    fun isAiConfigured(): Boolean {
        val key = getApiKey()
        return key.isNotBlank() && key != "MY_GEMINI_API_KEY"
    }

    suspend fun getContextualExplanation(word: String, contextSentence: String): Result<AiExplanationResult> = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (!isAiConfigured()) {
            return@withContext Result.failure(IllegalStateException("Gemini API key is not configured. The app is using the free offline dictionary."))
        }

        val prompt = """
            You are a helpful bilingual English-Urdu tutor assisting an ESL student reading a PDF.
            Word to explain: "$word"
            Surrounding sentence from PDF: "$contextSentence"

            Provide your response strictly in the following JSON format without markdown code blocks:
            {
              "explanationUrdu": "سادہ اور تفصیلی اردو وضاحت",
              "explanationEnglish": "Simple explanation in easy English for learners",
              "contextNotes": "Brief note on why the word means this in this specific sentence",
              "suggestedSynonyms": ["synonym1", "synonym2", "synonym3"],
              "sentenceTranslation": "با محاورہ اور سلیس اردو ترجمہ برائے جملہ"
            }
        """.trimIndent()

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"
            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            put(JSONObject().apply { put("text", prompt) })
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("AI request error: HTTP ${response.code}"))
            }

            val rootJson = JSONObject(responseBody)
            val candidates = rootJson.optJSONArray("candidates")
            val text = candidates?.optJSONObject(0)
                ?.optJSONObject("content")
                ?.optJSONArray("parts")
                ?.optJSONObject(0)
                ?.optString("text") ?: ""

            val cleaned = text.replace("```json", "").replace("```", "").trim()
            val parsed = JSONObject(cleaned)

            val synonymsArray = parsed.optJSONArray("suggestedSynonyms")
            val synonymsList = mutableListOf<String>()
            if (synonymsArray != null) {
                for (i in 0 until synonymsArray.length()) {
                    synonymsList.add(synonymsArray.getString(i))
                }
            }

            Result.success(
                AiExplanationResult(
                    explanationUrdu = parsed.optString("explanationUrdu", "اردو تشریح دستیاب ہے۔"),
                    explanationEnglish = parsed.optString("explanationEnglish", "English explanation loaded."),
                    contextNotes = parsed.optString("contextNotes", "Context applied."),
                    suggestedSynonyms = synonymsList,
                    sentenceTranslation = parsed.optString("sentenceTranslation", "")
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
