package com.example.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DrawerContentPreview() {
    DrawerContent(onCloseDrawer = {}, onNavigateToSuggested = {})
}

@Composable
fun DrawerContent(
    onCloseDrawer: () -> Unit,
    onNavigateToSuggested: () -> Unit
) {
    val darkBlueGrey = Color(0xFF2C353F)
    val textPrimary = Color.White
    val textSecondary = Color.LightGray
    val searchBg = Color(0xFF3B4652)

    var searchQuery by remember { mutableStateOf("") }
    var expandedItem by remember { mutableStateOf<String?>(null) }
    var selectedChat by remember { mutableStateOf("Herb Inventory") }

    val allChats = listOf(
        "The Bread Pizza Spec",
        "Cheese Melt Ratio",
        "Fridge Diagnostics",
        "Herb Inventory",
        "Dough Dynamics",
        "Oven Calibration",
        "Tuesday's Prep List",
        "The Grocery Audit"
    )
    val filteredChats = if (searchQuery.isBlank()) allChats
        else allChats.filter { it.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(darkBlueGrey)
            .padding(24.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp, top = 24.dp)
        ) {
            IconButton(onClick = onCloseDrawer, modifier = Modifier.size(40.dp).background(searchBg, RoundedCornerShape(20.dp))) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close", tint = textPrimary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text("Kora", color = textPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search", color = textSecondary) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = textSecondary) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = searchBg,
                unfocusedContainerColor = searchBg,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = textPrimary,
                unfocusedTextColor = textPrimary
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Suggested Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent, RoundedCornerShape(12.dp))
                .clickable { onNavigateToSuggested() }
                .padding(vertical = 12.dp)
        ) {
            Text("Suggested", color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recents
        Text("Recents", color = textSecondary, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(filteredChats) { chat ->
                val isSelected = chat == selectedChat
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isSelected) searchBg else Color.Transparent, RoundedCornerShape(12.dp))
                        .clickable {
                            selectedChat = chat
                            onCloseDrawer()
                        }
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    Text(chat, color = if (isSelected) textPrimary else textSecondary, fontSize = 16.sp)
                    if (isSelected) {
                        Box {
                            IconButton(onClick = { expandedItem = chat }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.MoreHoriz, contentDescription = "Options", tint = textPrimary)
                            }
                            DropdownMenu(
                                expanded = expandedItem == chat,
                                onDismissRequest = { expandedItem = null },
                                modifier = Modifier.background(searchBg)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Rename chat", color = textPrimary) },
                                    onClick = { expandedItem = null }
                                )
                                DropdownMenuItem(
                                    text = { Text("Delete chat", color = textPrimary) },
                                    onClick = { expandedItem = null }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // New Chat Button
        OutlinedButton(
            onClick = {
                selectedChat = ""
                onCloseDrawer()
            },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = textPrimary),
            border = androidx.compose.foundation.BorderStroke(1.dp, textSecondary),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.height(48.dp)
        ) {
            Text("New Chat", fontSize = 16.sp)
        }
    }
}
