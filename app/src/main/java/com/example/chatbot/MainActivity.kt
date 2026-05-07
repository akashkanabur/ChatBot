package com.example.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.chatbot.ui.DrawerContent
import com.example.chatbot.ui.FridgeScreen
import com.example.chatbot.ui.GroceryListScreen
import com.example.chatbot.ui.InventoryScreen
import com.example.chatbot.ui.MainScreen
import com.example.chatbot.ui.ProfileScreen
import com.example.chatbot.ui.Screen
import com.example.chatbot.ui.SuggestedScreen
import com.example.chatbot.ui.theme.ChatbotTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            ChatbotTheme(darkTheme = isDarkTheme) {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(
                            onCloseDrawer = { scope.launch { drawerState.close() } },
                            onNavigateToSuggested = {
                                currentScreen = Screen.Suggested
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                ) {
                    when (val screen = currentScreen) {
                        is Screen.Main -> MainScreen(
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onNavigate = { currentScreen = it }
                        )
                        is Screen.Suggested -> SuggestedScreen(
                            onNavigate = { currentScreen = it }
                        )
                        is Screen.Inventory -> InventoryScreen(
                            onNavigate = { currentScreen = it }
                        )
                        is Screen.GroceryList -> GroceryListScreen(
                            onNavigate = { currentScreen = it }
                        )
                        is Screen.Fridge -> FridgeScreen(
                            onNavigate = { currentScreen = it }
                        )
                        is Screen.Appliances -> com.example.chatbot.ui.AppliancesScreen(
                            onNavigate = { currentScreen = it }
                        )
                        is Screen.ApplianceDetail -> com.example.chatbot.ui.ApplianceDetailScreen(
                            applianceId = screen.applianceId,
                            onNavigate = { currentScreen = it }
                        )
                        is Screen.Profile -> ProfileScreen(
                            isDarkTheme = isDarkTheme,
                            onThemeToggle = { isDarkTheme = it },
                            onNavigate = { currentScreen = it }
                        )
                    }
                }
            }
        }
    }
}
