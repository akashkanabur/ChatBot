package com.example.chatbot.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onOpenDrawer = {}, onNavigate = {})
}

@Composable
fun MainScreen(
    onOpenDrawer: () -> Unit,
    onNavigate: (Screen) -> Unit
) {
    val gradientColors = listOf(Color(0xFF6DA5FF), Color(0xFFC8D7EB))
    val orbColor = Color(0xFF6B8EFF)
    val cardBgColor = Color(0x33FFFFFF) // Semi transparent white
    val cardBorderColor = Color(0x66FFFFFF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        // Blurred orb to make it glow (Layered behind)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .blur(60.dp)
        ) {
            val center = center
            val radius = size.width * 0.45f
            drawCircle(
                color = orbColor.copy(alpha = 0.8f),
                radius = radius,
                center = center
            )
        }

        // Glowing Orb Background Orbits
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.8f }
        ) {
            val center = center
            val radius = size.width * 0.4f
            
            // Draw central orb core
            drawCircle(
                color = orbColor,
                radius = radius,
                center = center
            )
            
            // Draw orbits
            drawOval(
                color = Color(0x44FFFFFF),
                topLeft = androidx.compose.ui.geometry.Offset(center.x - radius * 1.5f, center.y - radius * 0.4f),
                size = androidx.compose.ui.geometry.Size(radius * 3f, radius * 0.8f),
                style = Stroke(width = 4f)
            )
            drawOval(
                color = Color(0x44FFFFFF),
                topLeft = androidx.compose.ui.geometry.Offset(center.x - radius * 1.4f, center.y - radius * 0.5f),
                size = androidx.compose.ui.geometry.Size(radius * 2.8f, radius * 1f),
                style = Stroke(width = 4f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0x33FFFFFF), CircleShape)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Greeting Text
            Text(
                text = "Hello Heer,",
                color = Color.White,
                fontSize = 24.sp
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))) {
                        append("I am Kora.")
                    }
                },
                fontSize = 32.sp
            )
            Text(
                text = "your kitchen alive!",
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Expiring Soon Card
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .border(1.dp, cardBorderColor, RoundedCornerShape(24.dp))
                    .clickable { onNavigate(Screen.Suggested) }
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("You have:", color = Color.White, fontSize = 18.sp)
                        Text(
                            "2 items expiring soon.",
                            color = Color(0xFFF97316),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        Icons.Default.KeyboardDoubleArrowRight,
                        contentDescription = "Go",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Field
            var text by remember { mutableStateOf("") }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigate(Screen.Inventory) }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("What's cooking?", color = Color.White) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = cardBgColor,
                        unfocusedContainerColor = cardBgColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, cardBorderColor, RoundedCornerShape(32.dp)),
                    trailingIcon = {
                        Row(
                            modifier = Modifier.padding(end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { if (text.isNotBlank()) text = "" },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                            }
                            IconButton(
                                onClick = { /* TODO: voice input */ },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Mic, contentDescription = "Mic", tint = Color.White)
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(48.dp)) // Give space for bottom nav
        }

        // Bottom Navigation Bar
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.Main, onNavigate = onNavigate)
        }
    }
}
