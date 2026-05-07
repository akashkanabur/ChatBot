package com.example.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FridgeScreenPreview() {
    FridgeScreen(onNavigate = {})
}

@Composable
fun FridgeScreen(onNavigate: (Screen) -> Unit) {
    val gradientColors = listOf(Color(0xFF6DA5FF), Color(0xFFC8D7EB))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp, bottom = 80.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                IconButton(
                    onClick = { onNavigate(Screen.Inventory) },
                    modifier = Modifier.size(48.dp).background(Color(0x33FFFFFF), CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Fridge", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Fridge contents coming soon.", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.Fridge, onNavigate = onNavigate)
        }
    }
}
