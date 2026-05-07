package com.example.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun SuggestedScreenPreview() {
    SuggestedScreen(onNavigate = {})
}

@Composable
fun SuggestedScreen(
    onNavigate: (Screen) -> Unit
) {
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
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                IconButton(
                    onClick = { onNavigate(Screen.Main) },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0x33FFFFFF), CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Suggested", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
            
            // Expiring Soon
            Text("Expiring Soon", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
            
            ExpiringItemCard("Cheese", "2 days left", "4 slices", "Fridge")
            Spacer(modifier = Modifier.height(16.dp))
            ExpiringItemCard("Bread", "3 days left", "1/2 pound", "Pantry")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Dishes
            Text("Dishes", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DishCard(
                    title = "Cheese Sandwich",
                    makeTime = "7-10 minutes",
                    useOf = "Griller",
                    modifier = Modifier.weight(1f)
                )
                DishCard(
                    title = "Bread Pizza",
                    makeTime = "17-20 minutes",
                    useOf = "Oven",
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.Suggested, onNavigate = onNavigate)
        }
    }
}

@Composable
fun ExpiringItemCard(name: String, timeLeft: String, quantity: String, location: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF6B6B), RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, color = Color.White, fontWeight = FontWeight.Bold)
        Text(timeLeft, color = Color.White, fontWeight = FontWeight.Bold)
        Text(quantity, color = Color.White)
        Text(location, color = Color.White)
    }
}

@Composable
fun DishCard(title: String, makeTime: String, useOf: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF)),
        modifier = modifier.height(200.dp)
    ) {
        Column {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray)
            )
            
            Column(modifier = Modifier.padding(12.dp)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Make Time: $makeTime", color = Color.White, fontSize = 12.sp)
                Text("Use of: $useOf", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}
