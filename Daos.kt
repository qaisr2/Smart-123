package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.SavedWord
import com.example.model.SearchHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedWordDao {
    @Query("SELECT * FROM saved_words ORDER BY timestamp DESC")
    fun getAllSavedWords(): Flow<List<SavedWord>>

    @Query("SELECT * FROM saved_words WHERE word = :word LIMIT 1")
    suspend fun getWord(word: String): SavedWord?

    @Query("SELECT COUNT(*) FROM saved_words WHERE word = :word")
    suspend fun isWordSaved(word: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: SavedWord): Long

    @Update
    suspend fun update(word: SavedWord)

    @Delete
    suspend fun delete(word: SavedWord)

    @Query("DELETE FROM saved_words WHERE word = :word")
    suspend fun deleteByWord(word: String)

    @Query("UPDATE saved_words SET masteryLevel = :level WHERE id = :id")
    suspend fun updateMastery(id: Long, level: Int)
}

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 50")
    fun getRecentHistory(): Flow<List<SearchHistoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SearchHistoryItem)

    @Query("DELETE FROM search_history WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM search_history")
    suspend fun clearAll()
}
