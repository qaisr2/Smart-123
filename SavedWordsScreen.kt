package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SavedWord
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SavedWordsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val savedWords by viewModel.savedWords.collectAsState()
    var searchFilter by remember { mutableStateOf("") }
    var selectedFilterLevel by remember { mutableIntStateOf(-1) } // -1 = All, 0 = Learning, 1 = Reviewing, 2 = Mastered

    val filteredList = remember(savedWords, searchFilter, selectedFilterLevel) {
        savedWords.filter { word ->
            val matchesSearch = searchFilter.isBlank() ||
                    word.word.contains(searchFilter, ignoreCase = true) ||
                    word.urduMeaning.contains(searchFilter) ||
                    word.romanUrdu.contains(searchFilter, ignoreCase = true)

            val matchesLevel = selectedFilterLevel == -1 || word.masteryLevel == selectedFilterLevel

            matchesSearch && matchesLevel
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("saved_words_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Saved Vocabulary (${savedWords.size})",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Search & Filter header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            OutlinedTextField(
                value = searchFilter,
                onValueChange = { searchFilter = it },
                placeholder = { Text("Filter saved words or Urdu…", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(20.dp)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("saved_words_search_filter"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilterLevel == -1,
                    onClick = { selectedFilterLevel = -1 },
                    label = { Text("All (${savedWords.size})") }
                )
                FilterChip(
                    selected = selectedFilterLevel == 0,
                    onClick = { selectedFilterLevel = 0 },
                    label = { Text("Learning (${savedWords.count { it.masteryLevel == 0 }})") }
                )
                FilterChip(
                    selected = selectedFilterLevel == 1,
                    onClick = { selectedFilterLevel = 1 },
                    label = { Text("Reviewing (${savedWords.count { it.masteryLevel == 1 }})") }
                )
                FilterChip(
                    selected = selectedFilterLevel == 2,
                    onClick = { selectedFilterLevel = 2 },
                    label = { Text("Mastered (${savedWords.count { it.masteryLevel == 2 }})") }
                )
            }
        }

        if (filteredList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.outlineVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (savedWords.isEmpty()) "No words saved yet!" else "No matching words found.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap the bookmark icon in the PDF reader to save difficult vocabulary words for spaced revision.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList, key = { it.id }) { word ->
                    SavedWordItemCard(
                        word = word,
                        onSpeak = { viewModel.speakText(word.word) },
                        onDelete = { viewModel.deleteSavedWord(word) },
                        onLevelChange = { newLevel -> viewModel.updateWordMastery(word, newLevel) }
                    )
                }
            }
        }
    }
}

@Composable
fun SavedWordItemCard(
    word: SavedWord,
    onSpeak: () -> Unit,
    onDelete: () -> Unit,
    onLevelChange: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("saved_word_card_${word.word}"),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = word.word.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    if (word.partOfSpeech.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = word.partOfSpeech,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Row {
                    IconButton(onClick = onSpeak, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Pronounce", modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                    }
                }
            }

            // Urdu meaning in RTL
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    text = word.urduMeaning,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            // Roman Urdu
            if (word.romanUrdu.isNotBlank()) {
                Text(
                    text = "Roman: ${word.romanUrdu}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            }

            // Context sentence
            if (word.contextSentence.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "\"${word.contextSentence}\"",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            // Mastery status toggles
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mastery:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilterChip(
                        selected = word.masteryLevel == 0,
                        onClick = { onLevelChange(0) },
                        label = { Text("Learning", fontSize = 11.sp) }
                    )
                    FilterChip(
                        selected = word.masteryLevel == 1,
                        onClick = { onLevelChange(1) },
                        label = { Text("Reviewing", fontSize = 11.sp) }
                    )
                    FilterChip(
                        selected = word.masteryLevel == 2,
                        onClick = { onLevelChange(2) },
                        label = { Text("Mastered", fontSize = 11.sp) }
                    )
                }
            }
        }
    }
}
