package com.example.chatbot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6B8EFF),
    secondary = Color(0xFF6DA5FF),
    background = Color(0xFF1A1F2E),
    surface = Color(0xFF2C353F),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6B8EFF),
    secondary = Color(0xFF6DA5FF),
    background = Color(0xFFF0F4FF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onBackground = Color(0xFF1A1F2E),
    onSurface = Color(0xFF1A1F2E)
)

@Composable
fun ChatbotTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}