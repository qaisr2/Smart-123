package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.ReaderScreen
import com.example.ui.screens.SavedWordsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainContent(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    // Handle back button: return to Reader screen from any sub-screen
    BackHandler(enabled = currentScreen != AppScreen.READER) {
        viewModel.setScreen(AppScreen.READER)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            BottomNavBar(
                currentScreen = currentScreen,
                onScreenSelected = { screen -> viewModel.setScreen(screen) }
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (currentScreen) {
            AppScreen.READER -> ReaderScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.SAVED_WORDS -> SavedWordsScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.PRACTICE -> PracticeScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.HISTORY -> HistoryScreen(viewModel = viewModel, modifier = modifier)
        }
    }
}
