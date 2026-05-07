package com.example.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun GroceryListScreenPreview() {
    GroceryListScreen(onNavigate = {})
}

@Composable
fun GroceryListScreen(onNavigate: (Screen) -> Unit) {
    val gradientColors = listOf(Color(0xFF6DA5FF), Color(0xFFC8D7EB))
    var newItem by remember { mutableStateOf("") }
    val items = remember { mutableStateListOf("Milk", "Eggs", "Butter", "Tomatoes") }

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
                Text("Grocery List", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            items.forEach { item ->
                var checked by remember { mutableStateOf(false) }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(Color(0x33FFFFFF), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = CheckboxDefaults.colors(checkmarkColor = Color.White, checkedColor = Color(0xFF6B8EFF))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(item, color = Color.White, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = newItem,
                    onValueChange = { newItem = it },
                    placeholder = { Text("Add item...", color = Color.White.copy(alpha = 0.6f)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0x33FFFFFF),
                        unfocusedContainerColor = Color(0x33FFFFFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (newItem.isNotBlank()) {
                            items.add(newItem.trim())
                            newItem = ""
                        }
                    },
                    modifier = Modifier.size(48.dp).background(Color(0xFF6B8EFF), CircleShape)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.GroceryList, onNavigate = onNavigate)
        }
    }
}
