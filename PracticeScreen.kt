package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.dictionary.DictionaryData
import com.example.model.DictionaryEntry
import com.example.model.SavedWord
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Flashcards, 1 = Quiz
    val savedWords by viewModel.savedWords.collectAsState()
    val practiceIndex by viewModel.practiceCardIndex.collectAsState()
    val isCardFlipped by viewModel.isCardFlipped.collectAsState()

    // Active flashcard items: prefer user's saved words, or fallback to rich dictionary sample
    val practiceItems: List<PracticeItem> = remember(savedWords) {
        if (savedWords.isNotEmpty()) {
            savedWords.map {
                PracticeItem(
                    word = it.word,
                    urduMeaning = it.urduMeaning,
                    romanUrdu = it.romanUrdu,
                    partOfSpeech = it.partOfSpeech,
                    simpleEnglish = it.simpleEnglish,
                    contextSentence = it.contextSentence,
                    savedWordRef = it
                )
            }
        } else {
            DictionaryData.entries.values.take(15).map {
                PracticeItem(
                    word = it.word,
                    urduMeaning = it.urduMeaning,
                    romanUrdu = it.romanUrdu,
                    partOfSpeech = it.partOfSpeech,
                    simpleEnglish = it.simpleEnglish,
                    contextSentence = it.exampleSentence,
                    savedWordRef = null
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("practice_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Practice & Revision",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        PrimaryTabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Flashcards") },
                icon = { Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp)) },
                modifier = Modifier.testTag("tab_flashcards")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Vocabulary Quiz") },
                icon = { Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(18.dp)) },
                modifier = Modifier.testTag("tab_quiz")
            )
        }

        if (selectedTab == 0) {
            FlashcardRevisionView(
                items = practiceItems,
                currentIndex = practiceIndex,
                isFlipped = isCardFlipped,
                onFlip = { viewModel.flipPracticeCard() },
                onNext = { viewModel.nextPracticeCard(practiceItems.size) },
                onSpeak = { viewModel.speakText(it) },
                onMarkMastery = { item, level ->
                    item.savedWordRef?.let { viewModel.updateWordMastery(it, level) }
                    viewModel.nextPracticeCard(practiceItems.size)
                }
            )
        } else {
            MultipleChoiceQuizView(
                items = practiceItems,
                onSpeak = { viewModel.speakText(it) }
            )
        }
    }
}

data class PracticeItem(
    val word: String,
    val urduMeaning: String,
    val romanUrdu: String,
    val partOfSpeech: String,
    val simpleEnglish: String,
    val contextSentence: String,
    val savedWordRef: SavedWord?
)

@Composable
fun FlashcardRevisionView(
    items: List<PracticeItem>,
    currentIndex: Int,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onNext: () -> Unit,
    onSpeak: (String) -> Unit,
    onMarkMastery: (PracticeItem, Int) -> Unit
) {
    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No flashcard items available.")
        }
        return
    }

    val safeIndex = currentIndex.coerceIn(0, items.size - 1)
    val item = items[safeIndex]

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = "card_flip"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Card ${safeIndex + 1} of ${items.size}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Flippable Card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .clickable { onFlip() }
                .testTag("flashcard_flip_target"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = if (isFlipped) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (rotation <= 90f) {
                    // FRONT OF CARD (English Word)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.word.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = item.partOfSpeech,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        IconButton(
                            onClick = { onSpeak(item.word) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Pronounce",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Flip, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.outline)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Tap card to reveal Urdu meaning",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                } else {
                    // BACK OF CARD (Urdu Meaning & Explanations, rendered upright)
                    Column(
                        modifier = Modifier
                            .graphicsLayer { rotationY = 180f }
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Text(
                                text = item.urduMeaning,
                                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Roman: ${item.romanUrdu}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = item.simpleEnglish,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (item.contextSentence.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "\"${item.contextSentence}\"",
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mastery & Next controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { onMarkMastery(item, 0) },
                modifier = Modifier.weight(1f).testTag("flashcard_learning_btn"),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Still Learning")
            }

            Button(
                onClick = { onMarkMastery(item, 2) },
                modifier = Modifier.weight(1f).testTag("flashcard_mastered_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("I Know This!")
            }

            IconButton(onClick = onNext, modifier = Modifier.size(48.dp).testTag("flashcard_next_btn")) {
                Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = "Next Card")
            }
        }
    }
}

@Composable
fun MultipleChoiceQuizView(
    items: List<PracticeItem>,
    onSpeak: (String) -> Unit
) {
    if (items.size < 4) {
        Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
            Text("Save at least 4 words to start the multiple-choice quiz!", textAlign = TextAlign.Center)
        }
        return
    }

    var questionIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    var isSubmitted by remember { mutableStateOf(false) }

    val currentItem = items[questionIndex % items.size]

    // Form 4 options: 1 correct, 3 distractors
    val options = remember(questionIndex) {
        val correct = currentItem.urduMeaning.split("/").first().trim()
        val distractors = items
            .filter { it.word != currentItem.word }
            .map { it.urduMeaning.split("/").first().trim() }
            .distinct()
            .shuffled()
            .take(3)

        (distractors + correct).shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Question ${(questionIndex % items.size) + 1} of ${items.size}", style = MaterialTheme.typography.labelMedium)
            Text("Score: $score", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "What is the correct Urdu meaning of:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = currentItem.word.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { onSpeak(currentItem.word) }) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Pronounce", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Options List
        options.forEachIndexed { index, optionText ->
            val isCorrect = optionText == currentItem.urduMeaning.split("/").first().trim()
            val isUserPick = selectedOption == index

            val containerColor = when {
                !isSubmitted -> if (isUserPick) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                isCorrect -> MaterialTheme.colorScheme.primaryContainer
                isUserPick -> MaterialTheme.colorScheme.errorContainer
                else -> MaterialTheme.colorScheme.surface
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable(enabled = !isSubmitted) {
                        selectedOption = index
                    }
                    .testTag("quiz_option_$index"),
                colors = CardDefaults.elevatedCardColors(containerColor = containerColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Right,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (isSubmitted) {
                        if (isCorrect) {
                            Icon(Icons.Default.Check, contentDescription = "Correct", tint = MaterialTheme.colorScheme.primary)
                        } else if (isUserPick) {
                            Icon(Icons.Default.Close, contentDescription = "Incorrect", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (!isSubmitted) {
            Button(
                onClick = {
                    if (selectedOption != -1) {
                        isSubmitted = true
                        val isCorrect = options[selectedOption] == currentItem.urduMeaning.split("/").first().trim()
                        if (isCorrect) score++
                    }
                },
                enabled = selectedOption != -1,
                modifier = Modifier.fillMaxWidth().testTag("quiz_submit_btn")
            ) {
                Text("Submit Answer")
            }
        } else {
            Button(
                onClick = {
                    questionIndex++
                    selectedOption = -1
                    isSubmitted = false
                },
                modifier = Modifier.fillMaxWidth().testTag("quiz_next_btn")
            ) {
                Text("Next Question")
            }
        }
    }
}
