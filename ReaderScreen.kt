package com.example.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.VerticalSplit
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DictionaryPanel
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ReadingViewMode
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val pdfTitle by viewModel.pdfTitle.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val totalPages by viewModel.totalPages.collectAsState()
    val pageBitmap by viewModel.currentPageBitmap.collectAsState()
    val pageText by viewModel.currentPageText.collectAsState()
    val isPdfLoading by viewModel.isPdfLoading.collectAsState()
    val readingMode by viewModel.readingMode.collectAsState()

    val selectedWord by viewModel.selectedWord.collectAsState()
    val surroundingSentence by viewModel.surroundingSentence.collectAsState()
    val currentEntry by viewModel.currentEntry.collectAsState()
    val sentenceTranslation by viewModel.sentenceTranslation.collectAsState()
    val isWordSaved by viewModel.isWordSaved.collectAsState()
    val isPanelExpanded by viewModel.isPanelExpanded.collectAsState()

    val isAiEnabled by viewModel.isAiEnabled.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    val aiResult by viewModel.aiResult.collectAsState()
    val aiErrorMessage by viewModel.aiErrorMessage.collectAsState()

    val openPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = queryFileName(context, it) ?: "Document.pdf"
            viewModel.openPdfUri(it, fileName)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("reader_screen")
    ) {
        // Top App Bar with File Open and Title
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = pdfTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Page ${currentPage + 1} of $totalPages",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { openPdfLauncher.launch(arrayOf("application/pdf")) },
                    modifier = Modifier.testTag("open_pdf_button")
                ) {
                    Icon(
                        Icons.Default.FileOpen,
                        contentDescription = "Open PDF from device",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        // View Mode Switcher + Page Navigation Header
        Surface(
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Navigation controls
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { viewModel.previousPage() },
                            enabled = currentPage > 0,
                            modifier = Modifier.size(36.dp).testTag("prev_page_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Page")
                        }
                        Text(
                            text = "${currentPage + 1} / $totalPages",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                        IconButton(
                            onClick = { viewModel.nextPage() },
                            enabled = currentPage + 1 < totalPages,
                            modifier = Modifier.size(36.dp).testTag("next_page_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Page")
                        }
                    }

                    // Reading View Mode buttons
                    SingleChoiceSegmentedButtonRow {
                        SegmentedButton(
                            selected = readingMode == ReadingViewMode.SPLIT,
                            onClick = { viewModel.setReadingMode(ReadingViewMode.SPLIT) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
                            label = { Text("Split", fontSize = 11.sp) }
                        )
                        SegmentedButton(
                            selected = readingMode == ReadingViewMode.FULL_PAGE,
                            onClick = { viewModel.setReadingMode(ReadingViewMode.FULL_PAGE) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
                            label = { Text("Page", fontSize = 11.sp) }
                        )
                        SegmentedButton(
                            selected = readingMode == ReadingViewMode.TEXT_READER,
                            onClick = { viewModel.setReadingMode(ReadingViewMode.TEXT_READER) },
                            shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
                            label = { Text("Text", fontSize = 11.sp) }
                        )
                    }
                }
            }
        }

        // Main Content Area based on View Mode
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (isPdfLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Rendering PDF page…", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                when (readingMode) {
                    ReadingViewMode.SPLIT -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Top half: Zoomable PDF Page Bitmap
                            Box(
                                modifier = Modifier
                                    .weight(0.48f)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                            ) {
                                ZoomablePdfPage(
                                    bitmap = pageBitmap,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            // Bottom half: Extracted Interactive Text Reader
                            Box(
                                modifier = Modifier
                                    .weight(0.52f)
                                    .fillMaxWidth()
                            ) {
                                InteractiveTextReader(
                                    text = pageText,
                                    selectedWord = selectedWord,
                                    onWordClick = { word, sentence ->
                                        viewModel.lookupWord(word, sentence)
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    ReadingViewMode.FULL_PAGE -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                        ) {
                            ZoomablePdfPage(
                                bitmap = pageBitmap,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    ReadingViewMode.TEXT_READER -> {
                        InteractiveTextReader(
                            text = pageText,
                            selectedWord = selectedWord,
                            onWordClick = { word, sentence ->
                                viewModel.lookupWord(word, sentence)
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        // Docked Bilingual Dictionary Tutor Panel
        DictionaryPanel(
            entry = currentEntry,
            selectedWord = selectedWord,
            surroundingSentence = surroundingSentence,
            sentenceTranslation = sentenceTranslation,
            isWordSaved = isWordSaved,
            isPanelExpanded = isPanelExpanded,
            onToggleExpand = { viewModel.togglePanelExpanded() },
            onSaveToggle = { viewModel.toggleSaveCurrentWord() },
            onSpeak = { text -> viewModel.speakText(text) },
            onSearchWord = { word -> viewModel.lookupWord(word) },
            isAiEnabled = isAiEnabled,
            onToggleAi = { enabled -> viewModel.setAiEnabled(enabled) },
            isAiLoading = isAiLoading,
            aiResult = aiResult,
            aiErrorMessage = aiErrorMessage,
            onFetchAi = { viewModel.fetchAiExplanation() },
            isAiKeyPresent = viewModel.isAiKeyPresent,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ZoomablePdfPage(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier
) {
    if (bitmap == null) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("No page to display", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        return
    }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 4f)
                    offset = if (scale > 1f) {
                        Offset(offset.x + pan.x, offset.y + pan.y)
                    } else {
                        Offset.Zero
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = if (scale > 1f) 1f else 2f
                        offset = Offset.Zero
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "PDF Page View",
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .fillMaxSize()
        )

        // Floating zoom controls overlay
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f), CircleShape)
                .padding(2.dp)
        ) {
            IconButton(
                onClick = {
                    scale = (scale - 0.5f).coerceAtLeast(1f)
                    if (scale == 1f) offset = Offset.Zero
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(Icons.Default.ZoomOut, contentDescription = "Zoom out", modifier = Modifier.size(18.dp))
            }
            IconButton(
                onClick = { scale = (scale + 0.5f).coerceAtMost(4f) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(Icons.Default.ZoomIn, contentDescription = "Zoom in", modifier = Modifier.size(18.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveTextReader(
    text: String,
    selectedWord: String,
    onWordClick: (word: String, sentence: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Break into paragraphs and sentences
    val paragraphs = remember(text) {
        text.split(Regex("\n\\s*\n|\r\n\\s*\r\n"))
            .filter { it.isNotBlank() }
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(14.dp)
            .testTag("interactive_text_reader")
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💡 Tap any word to see Urdu meaning, Roman Urdu, and sentence translation below.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        if (paragraphs.isEmpty()) {
            Text(
                text = "No selectable text found on this page. You can still use the dictionary bar below to look up any word.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        paragraphs.forEachIndexed { pIdx, paragraph ->
            // Clean paragraph tokens
            val rawTokens = paragraph.split(Regex("\\s+"))

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rawTokens.forEach { token ->
                    val cleanWord = token.replace(Regex("[^a-zA-Z]"), "")
                    val isSelected = cleanWord.isNotBlank() && cleanWord.equals(selectedWord, ignoreCase = true)

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else androidx.compose.ui.graphics.Color.Transparent
                            )
                            .clickable(enabled = cleanWord.isNotBlank()) {
                                onWordClick(cleanWord, paragraph.trim())
                            }
                            .padding(horizontal = 3.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = token,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

private fun queryFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) {
                    result = it.getString(index)
                }
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result
}
