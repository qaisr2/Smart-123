package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.viewmodel.AppScreen

@Composable
fun BottomNavBar(
    currentScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = "PDF Reader") },
            label = { Text("Reader") },
            selected = currentScreen == AppScreen.READER,
            onClick = { onScreenSelected(AppScreen.READER) },
            modifier = Modifier.testTag("nav_reader")
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Bookmark, contentDescription = "Saved Words") },
            label = { Text("Saved") },
            selected = currentScreen == AppScreen.SAVED_WORDS,
            onClick = { onScreenSelected(AppScreen.SAVED_WORDS) },
            modifier = Modifier.testTag("nav_saved_words")
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.School, contentDescription = "Practice & Quiz") },
            label = { Text("Practice") },
            selected = currentScreen == AppScreen.PRACTICE,
            onClick = { onScreenSelected(AppScreen.PRACTICE) },
            modifier = Modifier.testTag("nav_practice")
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.History, contentDescription = "Search History") },
            label = { Text("History") },
            selected = currentScreen == AppScreen.HISTORY,
            onClick = { onScreenSelected(AppScreen.HISTORY) },
            modifier = Modifier.testTag("nav_history")
        )
    }
}
